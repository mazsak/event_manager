package com.example.event_manager.service;


import static java.util.stream.Collectors.groupingBy;

import com.example.event_manager.form.BillingForm;
import com.example.event_manager.form.EventForm;
import com.example.event_manager.form.EventWithBillingForm;
import com.example.event_manager.form.TaskStatusForm;
import com.example.event_manager.mapper.EventMapper;
import com.example.event_manager.mapper.EventWithBillingMapper;
import com.example.event_manager.mapper.TaskStatusMapper;
import com.example.event_manager.model.BillingRaportSchema;
import com.example.event_manager.model.BillingsSummary;
import com.example.event_manager.model.Event;
import com.example.event_manager.model.TaskStatus;
import com.example.event_manager.repo.EventRepo;
import com.example.event_manager.utils.BillingRaport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.xml.transform.TransformerException;
import lombok.RequiredArgsConstructor;
import org.apache.fop.apps.FOPException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

  private final EventRepo eventRepo;
  private final EventMapper eventMapper;
  private final TaskStatusService taskStatusService;
  private final TaskStatusMapper taskStatusMapper;
  private final EventWithBillingMapper eventWithBillingMapper;


  public boolean saveEventForm(final EventForm eventForm) {
    final Event eve = eventMapper.toEntity(eventForm);
    return save(eve);
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
    return eventRepo.findById(id).get();
  }

  @Override
  public EventForm eventFormById(final Long id) {
    return eventMapper.fromEntity(findById(id));
  }

  @Override
  public Map<String, List<TaskStatusForm>> preapreTasksForEvent(final EventForm event) {
    return event.getTaskStatuses().stream()
        .collect(groupingBy(TaskStatusForm::getTaskStatusType));
  }

  @Override
  public boolean deleteTaskStatusFromEvent(final Long taskId, final Long eventId) {
    final Event event = findById(eventId);
    final TaskStatus toDelete = taskStatusService.findById(taskId);
    event.getTaskStatuses().remove(toDelete);
    return save(event);
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
  public Map<String, List<Event>> getEventsPartition() { // It uses repo to call spring data jpa methods (3 queries) not yet paginated

    final Map<String, List<Event>> nameToListMap = new HashMap<>();

    nameToListMap
        .put("started", eventRepo.findAllByDateTimeIsAfterAndStartedIsTrue(LocalDateTime.now()));
    nameToListMap.put("notstarted",
        eventRepo.findAllByDateTimeIsAfterAndStartedIsFalse(LocalDateTime.now()));
    nameToListMap.put("outdated", eventRepo.findAllByDateTimeIsBefore(LocalDateTime.now()));

    return nameToListMap;
  }

  @Override
  public Map<String, List<Event>> searchByNamePlaceTopic(final String s) {
    final Map<String, List<Event>> nameToListMap = new HashMap<>();
    final List<Event> list = eventRepo.searchByNamePlaceTopic(s);

    nameToListMap.put("started",
        list.stream().filter(x -> x.getStarted())
            .filter(x -> x.getDateTime().isAfter(LocalDateTime.now()))
            .collect(Collectors.toList()));
    nameToListMap.put("notstarted",
        list.stream().filter(x -> !x.getStarted())
            .filter(x -> x.getDateTime().isAfter(LocalDateTime.now()))
            .collect(Collectors.toList()));
    nameToListMap.put("outdated",
        list.stream().filter(x -> x.getDateTime().isBefore(LocalDateTime.now())).collect(
            Collectors.toList()));
    return nameToListMap;

  }

  @Override
  public byte[] generateRaportOfBillingsForEvent(final Long id)
      throws TransformerException, IOException, FOPException {
    final EventWithBillingForm eventWithBillingForm = eventWithBillingMapper
        .toDto(this.findById(id));
    final List<BillingForm> listOfBilling = eventWithBillingForm.getBillings();
    final BillingsSummary bs = new BillingsSummary(listOfBilling);
    final BillingRaportSchema brs = new BillingRaportSchema();
    brs.setEventName(eventWithBillingForm.getName());
    brs.setBillings(listOfBilling);
    brs.setBillingsSummary(bs);

    final BillingRaport billingRaport = new BillingRaport(brs);
    return billingRaport.convertBillingRaportToByteStream();
  }
}
