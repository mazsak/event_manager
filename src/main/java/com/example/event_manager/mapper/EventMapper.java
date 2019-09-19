package com.example.event_manager.mapper;

import com.example.event_manager.form.BillingForm;
import com.example.event_manager.form.EventForm;
import com.example.event_manager.model.Billing;
import com.example.event_manager.model.Event;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

  List<EventForm> eventsToEventsDtos(List<Event> events);

  List<Event> eventsDTOsToEntities(List<EventForm> eventForms);

  Event toEntity(EventForm eventForm);

  BillingForm fromEntity(Billing billing);

  Billing toEntity(BillingForm billingForm);


  @Mapping(target = "taskStatuses", resultType = ArrayList.class)
  EventForm fromEntity(Event event);


}


