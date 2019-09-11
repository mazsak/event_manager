package com.example.event_manager.mapper;

import com.example.event_manager.form.TaskStatusForm;
import com.example.event_manager.model.TaskStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskStatusMapper {
  TaskStatusForm taskStatusToTaskStatusDto(TaskStatus taskStatus);

  TaskStatus toPOJO(TaskStatusForm taskStatusForm);

}
