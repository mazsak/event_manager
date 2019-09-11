package com.example.event_manager.service;


import com.example.event_manager.model.Event;
import com.example.event_manager.model.TaskStatus;
import com.example.event_manager.repo.EventRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

  private final EventRepo eventRepo;

  @Override
  public boolean save(final Event event) {
    return eventRepo.save(event) != null;
  }

  @Override
  public void delete(final Long id) {
    eventRepo.deleteById(id);
  }

  @Override
  public List<Event> findAll() {
    return eventRepo.findAll();
  }

  @Override
  public Event findById(final Long id) {
    return eventRepo.findById(id).get();
  }

  @Override
  public Map<String, List<TaskStatus>> preapreTasksForEvent(final Event event) {
    final Map<String, List<TaskStatus>> tasks = new HashMap<>();
    final Set<TaskStatus> allTaskStatus = event.getTaskStatuses();
    for (final TaskStatus ts : allTaskStatus) {
      tasks.computeIfAbsent(ts.getTaskStatusType(), k -> new ArrayList<>()).add(ts);
    }

    return tasks;
  }
}
