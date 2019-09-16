package com.example.event_manager.service;

import com.example.event_manager.model.Event;
import com.example.event_manager.model.TaskStatus;
import java.util.List;
import java.util.Map;

public interface EventService {

  boolean save(final Event event);

  void delete(final Long id);

  List<Event> findAll();

  Event findById(final Long id);

  Map<String, List<TaskStatus>> preapreTasksForEvent(final Event event);

  Map<String, List<Event>> getEventsPartition();
}
