package com.example.event_manager.mapper;

import com.example.event_manager.form.EventWithBillingForm;
import com.example.event_manager.model.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BillingMapper.class)
public interface EventWithBillingMapper extends BasicMapper<Event, EventWithBillingForm> {}
