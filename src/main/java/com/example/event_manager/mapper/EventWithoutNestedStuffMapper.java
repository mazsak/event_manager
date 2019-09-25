package com.example.event_manager.mapper;

import com.example.event_manager.form.EventWithoutNestedStuff;
import com.example.event_manager.model.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventWithoutNestedStuffMapper
        extends BasicMapper<Event, EventWithoutNestedStuff> {
}
