package com.example.event_manager.service;

import com.example.event_manager.form.TaskStatusForm;

public interface TaskStatusService extends BasicService<TaskStatusForm, Long> {
  void changeState(Long id);
}
