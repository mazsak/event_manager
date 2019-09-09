package com.example.event_manager.service;

import com.example.event_manager.model.Event;
import com.example.event_manager.model.TaskStatus;
import java.util.List;
import java.util.Map;

public interface EventService {

  boolean save(Event event);

  void delete(Long id);

  List<Event> findAll();

  Event findById(Long id);

  Map<String, List<TaskStatus>> preapreTasksForEvent(Event event);

}
