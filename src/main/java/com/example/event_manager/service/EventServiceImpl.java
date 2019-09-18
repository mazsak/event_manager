package com.example.event_manager.service;

import static java.util.stream.Collectors.groupingBy;

import com.example.event_manager.enums.GroupName;
import com.example.event_manager.enums.SortHowEnum;
import com.example.event_manager.exception.EventNotFoundException;
import com.example.event_manager.form.AllEventsForm;
import com.example.event_manager.form.BillingForm;
import com.example.event_manager.form.EventForm;
import com.example.event_manager.form.EventWithBillingForm;
import com.example.event_manager.form.GroupForm;
import com.example.event_manager.form.TaskStatusForm;
import com.example.event_manager.mapper.EventMapper;
import com.example.event_manager.mapper.EventWithBillingMapper;
import com.example.event_manager.mapper.TaskStatusMapper;
import com.example.event_manager.model.Billing;
import com.example.event_manager.model.BillingRaportSchema;
import com.example.event_manager.model.BillingsSummary;
import com.example.event_manager.model.Event;
import com.example.event_manager.model.TaskStatus;
import com.example.event_manager.repo.EventRepo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

  private final EventRepo eventRepo;
  private final EventMapper eventMapper;
  private final TaskStatusService taskStatusService;
  private final TaskStatusMapper taskStatusMapper;
  private final EventWithBillingMapper eventWithBillingMapper;
  private final BillingService billingService;

  @Override
  public void saveEventForm(final EventForm eventForm) {
    eventForm.setTaskStatuses(eventForm.getTaskStatuses());
    eventForm.setBillings(eventForm.getBillings());

    save(eventMapper.toEntity(eventForm));
  }

  @Override
  public boolean save(final Event event) {
    return eventRepo.save(event) != null;
  }

  @Override
  public void delete(final Long id) {
    eventRepo.deleteById(id);
  }

  @Override
  public List<EventForm> findAll() {
    return eventMapper.eventsToEventsDtos(eventRepo.findAll());
  }

  @Override
  public Event findById(final Long id) {
    final Optional<Event> event = eventRepo.findById(id);
    if (!event.isPresent()) {
      throw new EventNotFoundException();
    } else {
      return event.get();
    }
  }

  @Override
  public EventForm eventFormById(final Long id) {
    return eventMapper.fromEntity(findById(id));
  }

  @Override
  public Map<String, List<TaskStatusForm>> preapreTasksForEvent(final EventForm event) {
    return event.getTaskStatuses().stream().collect(groupingBy(TaskStatusForm::getTaskStatusType));
  }

  @Override
  public void deleteTaskStatusFromEvent(final Long taskId, final Long eventId) {
    final Event event = findById(eventId);
    final TaskStatus toDelete = taskStatusService.findById(taskId);
    event.getTaskStatuses().remove(toDelete);
    save(event);
  }

  @Override
  public void deleteBillingFromEvent(final Long billingId, final Long eventId) {
    final Event event = findById(eventId);
    final Billing toDelete = billingService.findById(billingId);
    event.getBillings().remove(toDelete);
    save(event);
  }

  @Override
  public void saveAdhocTaskToEvent(final TaskStatusForm taskStatusForm, final Long eventId) {
    taskStatusForm.setTaskStatusType("adhoc");
    taskStatusForm.setStatus(false);
    final TaskStatusForm returnedTS = taskStatusService.saveAndReturn(taskStatusForm);
    final Event event = findById(eventId);
    event.getTaskStatuses().add(taskStatusMapper.toPOJO(returnedTS));
    save(event);

  }

  @Override
  public AllEventsForm searchByNamePlaceTopic(final String s, final Pageable pagingS,
      final Pageable pagingN,
      final Pageable pagingO) {
    final Page<Event> started = eventRepo
        .searchStarted(s.toLowerCase(), LocalDateTime.now(), pagingS);
    final Page<Event> notStarted = eventRepo
        .searchNotStarted(s.toLowerCase(), LocalDateTime.now(), pagingN);
    final Page<Event> outdated = eventRepo
        .searchOutdated(s.toLowerCase(), LocalDateTime.now(), pagingO);
    return AllEventsForm.builder()
        .started(GroupForm.builder().groupName(GroupName.STARTED).pageOfEvents(started)
            .pageNumberOfCurrentGroup(started.getNumber())
            .pageSizeOfCurrentGroup(started.getSize())
            .pageNumberOfSecondGroup(notStarted.getNumber())
            .pageSizeOfSecondGroup(notStarted.getSize())
            .pageNumberOfThirdGroup(outdated.getNumber())
            .pageSizeOfThirdGroup(outdated.getSize())
            .query(s)
            .sortHowEnum(SortHowEnum.NAME_ASC).build())
        .notStarted(GroupForm.builder().groupName(GroupName.NOTSTARTED).pageOfEvents(notStarted)
            .pageNumberOfCurrentGroup(notStarted.getNumber())
            .pageSizeOfCurrentGroup(notStarted.getSize())
            .pageNumberOfSecondGroup(started.getNumber())
            .pageSizeOfSecondGroup(started.getSize())
            .pageNumberOfThirdGroup(outdated.getNumber())
            .pageSizeOfThirdGroup(outdated.getSize())
            .query(s)
            .sortHowEnum(SortHowEnum.NAME_ASC).build())
        .outdated(GroupForm.builder().groupName(GroupName.OUTDATED).pageOfEvents(outdated)
            .pageNumberOfCurrentGroup(outdated.getNumber())
            .pageSizeOfCurrentGroup(outdated.getSize())
            .pageNumberOfSecondGroup(started.getNumber())
            .pageSizeOfSecondGroup(started.getSize())
            .pageNumberOfThirdGroup(notStarted.getNumber())
            .pageSizeOfThirdGroup(notStarted.getSize())
            .query(s)
            .sortHowEnum(SortHowEnum.NAME_ASC).build())
        .query(s)
        .build();
  }

  @Override
  public AllEventsForm getPartition(final Pageable pagingS, final Pageable pagingN,
      final Pageable pagingO) {
    final Page<Event> started = eventRepo
        .findAllByDateTimeIsAfterAndStartedIsTrue(LocalDateTime.now(), pagingS);
    final Page<Event> notStarted = eventRepo
        .findAllByDateTimeIsAfterAndStartedIsFalse(LocalDateTime.now(), pagingN);
    final Page<Event> outdated = eventRepo.findAllByDateTimeIsBefore(LocalDateTime.now(), pagingO);
    return AllEventsForm.builder()
        .started(GroupForm.builder().groupName(GroupName.STARTED).pageOfEvents(started)
            .pageNumberOfCurrentGroup(started.getNumber())
            .pageSizeOfCurrentGroup(started.getSize())
            .pageNumberOfSecondGroup(notStarted.getNumber())
            .pageSizeOfSecondGroup(notStarted.getSize())
            .pageNumberOfThirdGroup(outdated.getNumber())
            .pageSizeOfThirdGroup(outdated.getSize())
            .query("")
            .sortHowEnum(SortHowEnum.NAME_ASC).build())
        .notStarted(GroupForm.builder().groupName(GroupName.NOTSTARTED).pageOfEvents(notStarted)
            .pageNumberOfCurrentGroup(notStarted.getNumber())
            .pageSizeOfCurrentGroup(notStarted.getSize())
            .pageNumberOfSecondGroup(started.getNumber())
            .pageSizeOfSecondGroup(started.getSize())
            .pageNumberOfThirdGroup(outdated.getNumber())
            .pageSizeOfThirdGroup(outdated.getSize())
            .query("")
            .sortHowEnum(SortHowEnum.NAME_ASC).build())
        .outdated(GroupForm.builder().groupName(GroupName.OUTDATED).pageOfEvents(outdated)
            .pageNumberOfCurrentGroup(outdated.getNumber())
            .pageSizeOfCurrentGroup(outdated.getSize())
            .pageNumberOfSecondGroup(started.getNumber())
            .pageSizeOfSecondGroup(started.getSize())
            .pageNumberOfThirdGroup(notStarted.getNumber())
            .pageSizeOfThirdGroup(notStarted.getSize())
            .query("")
            .sortHowEnum(SortHowEnum.NAME_ASC).build()).build();
  }

  @Override
  public BillingRaportSchema generateBillingRaportSchemaForEvent(final Long id) {
    final EventWithBillingForm eventWithBillingForm =
        eventWithBillingMapper.toDto(this.findById(id));
    final List<BillingForm> listOfBilling = eventWithBillingForm.getBillings();
    final BillingsSummary bs = new BillingsSummary(listOfBilling);
    final BillingRaportSchema brs = new BillingRaportSchema();
    brs.setEventName(eventWithBillingForm.getName());
    brs.setBillings(listOfBilling);
    brs.setBillingsSummary(bs);
    return brs;
  }

  @Override
  public void changeStarted(final Long id) {
    eventRepo.changeStarted(id);
  }
}
