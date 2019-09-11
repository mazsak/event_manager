package com.example.event_manager.service;


import com.example.event_manager.model.Event;
import com.example.event_manager.model.TaskStatus;
import com.example.event_manager.repo.EventRepo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

  private final EventRepo eventRepo;

  @Override
  public boolean save(final Event event) {
    return this.eventRepo.save(event) != null;
  }

  @Override
  public void delete(final Long id) {
    this.eventRepo.deleteById(id);
  }

  @Override
  public List<Event> findAll() {
    return this.eventRepo.findAll();
  }

  @Override
  public Event findById(final Long id) {
    return this.eventRepo.findById(id).get();
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
}
