package com.example.event_manager.service;

import com.example.event_manager.model.TaskStatus;

import java.util.List;

public interface TaskStatusService {
  boolean save(TaskStatus taskStatus);

  void delete(Long id);

  List<TaskStatus> findAll();

  TaskStatus findById(Long id);
}
