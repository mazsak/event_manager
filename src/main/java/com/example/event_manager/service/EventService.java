package com.example.event_manager.service;

import com.example.event_manager.model.Event;
import com.example.event_manager.repo.IEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService implements IEventServie {

  @Autowired private IEventRepo eventRepo;

  @Override
  public boolean save(Event event) {
    Event save = eventRepo.save(event);
    return save != null;
  }

  @Override
  public void delete(Long id) {
    eventRepo.deleteById(id);
  }

  @Override
  public List<Event> findAll() {
    return eventRepo.findAll();
  }

  @Override
  public Event findById(Long id) {
    return eventRepo.findById(id).get();
  }
}
