package com.example.event_manager.service;

import com.example.event_manager.form.PersonForm;
import com.example.event_manager.mapper.PersonMapper;
import com.example.event_manager.model.Person;
import com.example.event_manager.repo.PersonRepo;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl
        extends BasicServiceImpI<Person, PersonForm, PersonRepo, PersonMapper, Long>
        implements PersonService {

  public PersonServiceImpl(final PersonRepo personRepo, final PersonMapper mapper) {
    super(personRepo, mapper);
  }
}
