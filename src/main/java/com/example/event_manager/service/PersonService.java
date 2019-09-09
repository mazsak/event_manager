package com.example.event_manager.service;

import com.example.event_manager.form.PersonForm;
import com.example.event_manager.model.Event;
import com.example.event_manager.model.Person;

import java.util.List;

public interface PersonService {
  boolean save(PersonForm personForm);

  void delete(Long id);

  List<PersonForm> findAll();

  PersonForm findById(Long id);
}
