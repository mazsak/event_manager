package com.example.event_manager.mapper;

import com.example.event_manager.form.ToDoPredefinedForm;
import com.example.event_manager.model.ToDoPredefined;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.TreeSet;

@Mapper(componentModel = "spring")
public interface ToDoPredefinedMapper {

  ToDoPredefinedForm personToDoPredefinedMapperDto(ToDoPredefined toDoPredefined);

  @Mapping(target = "tasks", resultType = TreeSet.class)
  ToDoPredefined toPOJO(ToDoPredefinedForm toDoPredefinedForm);

}
