package com.example.event_manager.mapper;

import com.example.event_manager.form.TaskStatusForm;
import com.example.event_manager.model.TaskStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PersonMapper.class)
public interface TaskStatusMapper extends BasicMapper<TaskStatus, TaskStatusForm> {

}
