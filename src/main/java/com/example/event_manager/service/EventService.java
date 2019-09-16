package com.example.event_manager.service;

import com.example.event_manager.form.EventForm;
import com.example.event_manager.form.TaskStatusForm;
import com.example.event_manager.model.Event;
import java.util.List;
import java.util.Map;

public interface EventService {

  boolean save(final Event event);

  boolean saveEventForm(final EventForm eventForm);

  void delete(final Long id);

  List<EventForm> findAll();

  Event findById(final Long id);

  EventForm eventFormById(final Long id);


  Map<String, List<TaskStatusForm>> preapreTasksForEvent(final EventForm eventForm);

  boolean deleteTaskStatusFromEvent(Long taskId, Long eventId);

  void saveAdhocTaskToEvent(TaskStatusForm taskStatusForm, Long eventId);

  Map<String, List<Event>> getEventsPartition();

  Map<String, List<Event>> searchByNamePlaceTopic(String s);

}
