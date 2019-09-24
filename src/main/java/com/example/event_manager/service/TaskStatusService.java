package com.example.event_manager.service;

import com.example.event_manager.form.TaskStatusForm;
import com.example.event_manager.model.TaskStatus;
import java.util.List;

public interface TaskStatusService {

  boolean save(final TaskStatus taskStatus);

  TaskStatusForm saveAndReturn(final TaskStatusForm taskStatus);

  void delete(final Long id);

  List<TaskStatusForm> findAll();

  TaskStatusForm taskStatusFormById(final Long id);

  void changeState(Long id);

  void update(TaskStatusForm ts);

  TaskStatus findById(final Long id);
}
