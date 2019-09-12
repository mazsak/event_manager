package com.example.event_manager.mapper;

import com.example.event_manager.form.ToDoPredefinedForm;
import com.example.event_manager.model.ToDoPredefined;
import java.util.List;
import java.util.TreeSet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ToDoPredefinedMapper {

  ToDoPredefinedForm personToDoPredefinedMapperDto(ToDoPredefined toDoPredefined);

  @Mapping(target = "tasks", resultType = TreeSet.class)
  ToDoPredefined toPOJO(ToDoPredefinedForm toDoPredefinedForm);

  List<ToDoPredefinedForm> mapListToForm(List<ToDoPredefined> toDoPredefined);
}
