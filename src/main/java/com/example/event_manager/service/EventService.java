package com.example.event_manager.service;

import com.example.event_manager.form.AllEventsForm;
import com.example.event_manager.form.EventForm;
import com.example.event_manager.form.TaskStatusForm;
import com.example.event_manager.model.BillingRaportSchema;
import com.example.event_manager.model.Event;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;

public interface EventService {

  boolean save(final Event event);

  void saveEventForm(final EventForm eventForm);

  void delete(final Long id);

  List<EventForm> findAll();

  Event findById(final Long id);

  EventForm eventFormById(final Long id);

  Map<String, List<TaskStatusForm>> preapreTasksForEvent(final EventForm eventForm);

  void deleteTaskStatusFromEvent(Long taskId, Long eventId);

  void deleteBillingFromEvent(Long bilingId, Long eventId);

  void saveAdhocTaskToEvent(TaskStatusForm taskStatusForm, Long eventId);

  AllEventsForm searchByNamePlaceTopic(String s, Pageable pagingS, Pageable pagingN,
      Pageable pagingO);

  BillingRaportSchema generateBillingRaportSchemaForEvent(final Long id)
      ;

  void changeStarted(Long id);
  AllEventsForm getPartition(Pageable pagingS, Pageable pagingN, Pageable pagingO);
}
