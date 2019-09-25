package com.example.event_manager.mapper;

import com.example.event_manager.form.ToDoPredefinedSimpleForm;
import com.example.event_manager.model.ToDoPredefined;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToDoPredefinedSimpleMapper
    extends BasicMapper<ToDoPredefined, ToDoPredefinedSimpleForm> {}
