package com.example.event_manager.service;

import com.example.event_manager.model.TaskStatus;
import com.example.event_manager.repo.TaskStatusRepo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskStatusServiceImpl implements TaskStatusService {

  private final TaskStatusRepo taskStatusRepo;

  @Override
  public boolean save(final TaskStatus taskStatus) {
    return taskStatusRepo.save(taskStatus) != null;
  }

  @Override
  public void delete(final Long id) {
    taskStatusRepo.deleteById(id);
  }

  @Override
  public List<TaskStatus> findAll() {
    return taskStatusRepo.findAll();
  }

  @Override
  public TaskStatus findById(final Long id) {
    return taskStatusRepo.findById(id).get();
  }
}
