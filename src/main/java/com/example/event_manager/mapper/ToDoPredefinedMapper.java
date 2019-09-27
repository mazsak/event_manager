package com.example.event_manager.mapper;

import com.example.event_manager.form.ToDoPredefinedForm;
import com.example.event_manager.model.ToDoPredefined;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToDoPredefinedMapper extends BasicMapper<ToDoPredefined, ToDoPredefinedForm> {

}
