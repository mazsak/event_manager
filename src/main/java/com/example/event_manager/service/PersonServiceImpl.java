package com.example.event_manager.service;

import com.example.event_manager.model.Person;
import com.example.event_manager.repo.PersonRepo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

  private final PersonRepo personRepo;

  @Override
  public boolean save(final Person person) {
    return personRepo.save(person) != null;
  }

  @Override
  public void delete(final Long id) {
    personRepo.deleteById(id);
  }

  @Override
  public List<Person> findAll() {
    return personRepo.findAll();
  }

  @Override
  public Person findById(final Long id) {
    return personRepo.findById(id).get();
  }
}
