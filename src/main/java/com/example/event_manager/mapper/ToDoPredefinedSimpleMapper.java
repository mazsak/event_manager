package com.example.event_manager.mapper;

import com.example.event_manager.form.ToDoPredefinedSimpleForm;
import com.example.event_manager.model.ToDoPredefined;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToDoPredefinedSimpleMapper {

  List<ToDoPredefinedSimpleForm> mapToFromList(List<ToDoPredefined> predefineds);

  ToDoPredefinedSimpleForm mapToFrom(ToDoPredefined predefined);

  ToDoPredefined mapToEntity(ToDoPredefinedSimpleForm predefinedSimpleForm);
}
