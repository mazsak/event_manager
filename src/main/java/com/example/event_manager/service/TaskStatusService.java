package com.example.event_manager.service;

import com.example.event_manager.form.TaskStatusForm;
import com.example.event_manager.model.TaskStatus;

import java.util.List;

public interface TaskStatusService {
  boolean save(TaskStatusForm taskStatusForm);

  void delete(Long id);

  List<TaskStatusForm> findAll();

  TaskStatusForm findById(Long id);
}
