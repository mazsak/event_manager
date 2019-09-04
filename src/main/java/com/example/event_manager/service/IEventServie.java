package com.example.event_manager.service;

import com.example.event_manager.model.Event;

import java.util.List;

public interface IEventServie {
  boolean save(Event event);

  void delete(Long id);

  List<Event> findAll();

  Event findById(Long id);
}
