package com.example.event_manager.mapper;

import com.example.event_manager.form.EventForm;
import com.example.event_manager.model.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

  EventForm eventToEventDto(Event event);

  Event toPOJO(EventForm eventForm);
}
