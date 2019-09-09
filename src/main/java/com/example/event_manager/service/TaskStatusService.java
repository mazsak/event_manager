package com.example.event_manager.service;

import com.example.event_manager.model.TaskStatus;
import java.util.List;

public interface TaskStatusService {

  boolean save(final TaskStatus taskStatus);

  void delete(final Long id);

  List<TaskStatus> findAll();

  TaskStatus findById(final Long id);

  void update(TaskStatus ts);
}
