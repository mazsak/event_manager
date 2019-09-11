package com.example.event_manager.service;

import com.example.event_manager.form.PersonForm;
import com.example.event_manager.model.Person;
import java.util.List;

public interface PersonService {

  boolean save(final PersonForm personForm);

  void delete(final Long id);

  List<PersonForm> findAll();

  Person findById(final Long id);

  PersonForm PersonFormById(final Long id);
}
