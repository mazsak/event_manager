package com.example.event_manager.service;


import com.example.event_manager.model.Event;
import com.example.event_manager.model.TaskStatus;
import com.example.event_manager.repo.EventRepo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventServiceImpl implements EventService {

  private final EventRepo eventRepo;

  @Override
  public boolean save(Event event) {
    return eventRepo.save(event) != null;
  }

  @Override
  public void delete(Long id) {
    eventRepo.deleteById(id);
  }

  @Override
  public List<Event> findAll() {
    return eventRepo.findAll();
  }

  @Override
  public Event findById(Long id) {
    return eventRepo.findById(id).get();
  }

  @Override
  public Map<String, List<TaskStatus>> preapreTasksForEvent(Event event) {
    Map<String, List<TaskStatus>> tasks = new HashMap<>();
    Set<TaskStatus> allTaskStatus = event.getTaskStatuses();
    for (TaskStatus ts : allTaskStatus) {
      tasks.computeIfAbsent(ts.getTaskStatusType(), k -> new ArrayList<>()).add(ts);
    }

    return tasks;
  }
}
