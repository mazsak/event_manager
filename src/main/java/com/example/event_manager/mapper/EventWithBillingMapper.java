package com.example.event_manager.mapper;

import com.example.event_manager.form.BillingForm;
import com.example.event_manager.form.EventWithBillingForm;
import com.example.event_manager.model.Billing;
import com.example.event_manager.model.Event;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventWithBillingMapper {

  List<EventWithBillingForm> eventsToEventsWithBillingsDtos(List<Event> events);

  List<Event> eventsWithBillingsDTOsToEntities(List<EventWithBillingForm> eventForms);

  Event toEntity(EventWithBillingForm eventsWithBilling);

  EventWithBillingForm toDto(Event event);

  BillingForm fromEntity(Billing billing);


}
