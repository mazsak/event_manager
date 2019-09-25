package com.example.event_manager.service;

import com.example.event_manager.form.TaskStatusForm;
import com.example.event_manager.mapper.TaskStatusMapper;
import com.example.event_manager.model.TaskStatus;
import com.example.event_manager.repo.TaskStatusRepo;
import org.springframework.stereotype.Service;

@Service
public class TaskStatusServiceImpl
        extends BasicServiceImpI<TaskStatus, TaskStatusForm, TaskStatusRepo, TaskStatusMapper, Long>
        implements TaskStatusService {

  public TaskStatusServiceImpl(final TaskStatusRepo taskStatusRepo, final TaskStatusMapper mapper) {
    super(taskStatusRepo, mapper);
  }

  @Override
  public void changeState(final Long id) {
    repo.changeState(id);
  }
}
