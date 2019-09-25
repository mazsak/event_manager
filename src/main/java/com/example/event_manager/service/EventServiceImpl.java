package com.example.event_manager.service;

import com.example.event_manager.enums.GroupName;
import com.example.event_manager.enums.SortHowEnum;
import com.example.event_manager.form.AllEventsForm;
import com.example.event_manager.form.BillingForm;
import com.example.event_manager.form.EventForm;
import com.example.event_manager.form.EventWithBillingForm;
import com.example.event_manager.form.GroupForm;
import com.example.event_manager.mapper.EventMapper;
import com.example.event_manager.mapper.EventWithBillingMapper;
import com.example.event_manager.mapper.EventWithoutNestedStuffMapper;
import com.example.event_manager.model.BillingRaportSchema;
import com.example.event_manager.model.BillingsSummary;
import com.example.event_manager.model.Event;
import com.example.event_manager.repo.EventRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventServiceImpl
        extends BasicServiceImpI<Event, EventForm, EventRepo, EventMapper, Long>
        implements EventService {

  private final EventWithBillingMapper eventWithBillingMapper;
  private final EventWithoutNestedStuffMapper eventWithoutNestedStuffMapper;

  public EventServiceImpl(
          final EventRepo eventRepo,
          final EventMapper mapper,
          final EventWithBillingMapper eventWithBillingMapper,
          final EventWithoutNestedStuffMapper eventWithoutNestedStuffMapper) {
    super(eventRepo, mapper);
    this.eventWithBillingMapper = eventWithBillingMapper;
    this.eventWithoutNestedStuffMapper = eventWithoutNestedStuffMapper;
  }

  @Override
  public AllEventsForm searchByNamePlaceTopic(
          final String query, final Pageable pagingS, final Pageable pagingN, final Pageable pagingO) {
    final Page<Event> started =
            repo.searchStarted(query.toLowerCase(), LocalDateTime.now(), pagingS);
    final Page<Event> notStarted =
            repo.searchNotStarted(query.toLowerCase(), LocalDateTime.now(), pagingN);
    final Page<Event> outdated =
            repo.searchOutdated(query.toLowerCase(), LocalDateTime.now(), pagingO);
    return AllEventsForm.builder()
            .started(
                    GroupForm.builder()
                            .groupName(GroupName.STARTED)
                            .pageOfEvents(
                                    PageableExecutionUtils.getPage(
                                            eventWithoutNestedStuffMapper.mapToDTOList(started.getContent()),
                                            pagingS,
                                            started::getTotalElements))
                            .pageNumberOfCurrentGroup(started.getNumber())
                            .pageSizeOfCurrentGroup(started.getSize())
                            .pageNumberOfSecondGroup(notStarted.getNumber())
                            .pageSizeOfSecondGroup(notStarted.getSize())
                            .pageNumberOfThirdGroup(outdated.getNumber())
                            .pageSizeOfThirdGroup(outdated.getSize())
                            .query(query)
                            .sortHowEnum(SortHowEnum.NAME_ASC)
                            .build())
            .notStarted(
                    GroupForm.builder()
                            .groupName(GroupName.NOTSTARTED)
                            .pageOfEvents(
                                    PageableExecutionUtils.getPage(
                                            eventWithoutNestedStuffMapper.mapToDTOList(notStarted.getContent()),
                                            pagingN,
                                            notStarted::getTotalElements))
                            .pageNumberOfCurrentGroup(notStarted.getNumber())
                            .pageSizeOfCurrentGroup(notStarted.getSize())
                            .pageNumberOfSecondGroup(started.getNumber())
                            .pageSizeOfSecondGroup(started.getSize())
                            .pageNumberOfThirdGroup(outdated.getNumber())
                            .pageSizeOfThirdGroup(outdated.getSize())
                            .query(query)
                            .sortHowEnum(SortHowEnum.NAME_ASC)
                            .build())
            .outdated(
                    GroupForm.builder()
                            .groupName(GroupName.OUTDATED)
                            .pageOfEvents(
                                    PageableExecutionUtils.getPage(
                                            eventWithoutNestedStuffMapper.mapToDTOList(outdated.getContent()),
                                            pagingO,
                                            outdated::getTotalElements))
                            .pageNumberOfCurrentGroup(outdated.getNumber())
                            .pageSizeOfCurrentGroup(outdated.getSize())
                            .pageNumberOfSecondGroup(started.getNumber())
                            .pageSizeOfSecondGroup(started.getSize())
                            .pageNumberOfThirdGroup(notStarted.getNumber())
                            .pageSizeOfThirdGroup(notStarted.getSize())
                            .query(query)
                            .sortHowEnum(SortHowEnum.NAME_ASC)
                            .build())
            .query(query)
        .build();
  }

  @Override
  public AllEventsForm getPartition(
          final Pageable pagingS, final Pageable pagingN, final Pageable pagingO) {
    final Page<Event> started =
            repo.findAllByDateTimeIsAfterAndStartedIsTrue(LocalDateTime.now(), pagingS);
    final Page<Event> notStarted =
            repo.findAllByDateTimeIsAfterAndStartedIsFalse(LocalDateTime.now(), pagingN);
    final Page<Event> outdated = repo.findAllByDateTimeIsBefore(LocalDateTime.now(), pagingO);
    return AllEventsForm.builder()
            .started(
                    GroupForm.builder()
                            .groupName(GroupName.STARTED)
                            .pageOfEvents(
                                    PageableExecutionUtils.getPage(
                                            eventWithoutNestedStuffMapper.mapToDTOList(started.getContent()),
                                            pagingS,
                                            started::getTotalElements))
                            .pageNumberOfCurrentGroup(started.getNumber())
                            .pageSizeOfCurrentGroup(started.getSize())
                            .pageNumberOfSecondGroup(notStarted.getNumber())
                            .pageSizeOfSecondGroup(notStarted.getSize())
                            .pageNumberOfThirdGroup(outdated.getNumber())
                            .pageSizeOfThirdGroup(outdated.getSize())
                            .query("")
                            .sortHowEnum(SortHowEnum.NAME_ASC)
                            .build())
            .notStarted(
                    GroupForm.builder()
                            .groupName(GroupName.NOTSTARTED)
                            .pageOfEvents(
                                    PageableExecutionUtils.getPage(
                                            eventWithoutNestedStuffMapper.mapToDTOList(notStarted.getContent()),
                                            pagingN,
                                            notStarted::getTotalElements))
                            .pageNumberOfCurrentGroup(notStarted.getNumber())
                            .pageSizeOfCurrentGroup(notStarted.getSize())
                            .pageNumberOfSecondGroup(started.getNumber())
                            .pageSizeOfSecondGroup(started.getSize())
                            .pageNumberOfThirdGroup(outdated.getNumber())
                            .pageSizeOfThirdGroup(outdated.getSize())
                            .query("")
                            .sortHowEnum(SortHowEnum.NAME_ASC)
                            .build())
            .outdated(
                    GroupForm.builder()
                            .groupName(GroupName.OUTDATED)
                            .pageOfEvents(
                                    PageableExecutionUtils.getPage(
                                            eventWithoutNestedStuffMapper.mapToDTOList(outdated.getContent()),
                                            pagingO,
                                            outdated::getTotalElements))
                            .pageNumberOfCurrentGroup(outdated.getNumber())
                            .pageSizeOfCurrentGroup(outdated.getSize())
                            .pageNumberOfSecondGroup(started.getNumber())
                            .pageSizeOfSecondGroup(started.getSize())
                            .pageNumberOfThirdGroup(notStarted.getNumber())
                            .pageSizeOfThirdGroup(notStarted.getSize())
                            .query("")
                            .sortHowEnum(SortHowEnum.NAME_ASC)
                            .build())
            .build();
  }

  @Override
  public BillingRaportSchema generateBillingReportSchemaForEvent(final Long id) {
    final EventWithBillingForm eventWithBillingForm =
            eventWithBillingMapper.mapToDTO(repo.findById(id).get());
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
    repo.changeStarted(id);
  }
}
