package com.example.event_manager.mapper;

import com.example.event_manager.form.EventForm;
import com.example.event_manager.model.Event;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {TaskStatusMapper.class, BillingMapper.class})
public interface EventMapper extends BasicMapper<Event, EventForm> {

}
