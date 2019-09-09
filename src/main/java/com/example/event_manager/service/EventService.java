package com.example.event_manager.service;

import com.example.event_manager.form.EventForm;
import com.example.event_manager.model.Event;

import java.util.List;

public interface EventService {
  boolean save(EventForm eventForm);

  void delete(Long id);

  List<EventForm> findAll();

  EventForm findById(Long id);
}
