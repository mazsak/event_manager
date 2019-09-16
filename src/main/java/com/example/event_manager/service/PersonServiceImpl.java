package com.example.event_manager.service;

import com.example.event_manager.form.PersonForm;
import com.example.event_manager.mapper.TaskStatusMapper;
import com.example.event_manager.model.Person;
import com.example.event_manager.repo.PersonRepo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

  private final PersonRepo personRepo;
  private final TaskStatusMapper taskStatusMapper;

  @Override
  public boolean save(final PersonForm person) {
    return personRepo.save(taskStatusMapper.toEntity(person)) != null;
  }

  @Override
  public void delete(final Long id) {
    personRepo.deleteById(id);
  }

  @Override
  public List<PersonForm> findAll() {
    return taskStatusMapper.toPersonsDTO(personRepo.findAll());
  }

  @Override
  public Person findById(final Long id) {
    return personRepo.findById(id).get();
  }

  @Override
  public PersonForm PersonFormById(final Long id) {
    return taskStatusMapper.fromEntity(personRepo.findById(id).get());
  }
}
