package com.example.event_manager.service;

import com.example.event_manager.model.Person;
import com.example.event_manager.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
  
  private final PersonRepo personRepo;
  @Autowired
  public PersonServiceImpl(PersonRepo personRepo){
    this.personRepo = personRepo;
  }
  
  @Override
  public boolean save(Person person) {
    return personRepo.save(person) != null;
  }
  
  @Override
  public void delete(Long id) {
    personRepo.deleteById(id);
  }
  
  @Override
  public List<Person> findAll() {
    return personRepo.findAll();
  }
  
  @Override
  public Person findById(Long id) {
    return personRepo.findById(id).get();
  }
}
