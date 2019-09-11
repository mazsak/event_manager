package com.example.event_manager.mapper;

import com.example.event_manager.form.PersonForm;
import com.example.event_manager.form.TaskStatusForm;
import com.example.event_manager.model.Person;
import com.example.event_manager.model.TaskStatus;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskStatusMapper {

  List<TaskStatusForm> toDTOs(List<TaskStatus> entities);

  List<TaskStatus> toEntities(List<TaskStatusForm> forms);

  TaskStatusForm taskStatusToTaskStatusDto(TaskStatus taskStatus);

  TaskStatus toPOJO(TaskStatusForm taskStatusForm);

  List<PersonForm> toPersonsDTO(List<Person> p);

  PersonForm fromEntity(Person person);

  Person toEntity(PersonForm pf);


}
