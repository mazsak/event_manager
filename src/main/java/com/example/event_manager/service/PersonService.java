package com.example.event_manager.service;

import com.example.event_manager.model.Person;
import java.util.List;

public interface PersonService {

  boolean save(final Person personForm);

  void delete(final Long id);

  List<Person> findAll();

  Person findById(final Long id);
}
