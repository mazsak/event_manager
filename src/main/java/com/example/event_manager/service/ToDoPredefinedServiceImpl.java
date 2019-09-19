package com.example.event_manager.service;

import com.example.event_manager.form.ToDoPredefinedForm;
import com.example.event_manager.form.ToDoPredefinedSimpleForm;
import com.example.event_manager.mapper.ToDoPredefinedMapper;
import com.example.event_manager.mapper.ToDoPredefinedSimpleMapper;
import com.example.event_manager.repo.ToDoPredefinedRepo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToDoPredefinedServiceImpl implements ToDoPredefinedService {

  private final ToDoPredefinedRepo toDoPredefinedRepo;
  private final ToDoPredefinedMapper toDoPredefinedMapper;
  private final ToDoPredefinedSimpleMapper toDoPredefinedSimpleMapper;

  @Override
  public void save(final ToDoPredefinedForm toDoPredefined) {
    toDoPredefinedRepo.save(toDoPredefinedMapper.toPOJO(toDoPredefined));
  }

  @Override
  public void delete(final Long id) {
    toDoPredefinedRepo.deleteById(id);
  }

  @Override
  public List<ToDoPredefinedForm> findAll() {
    return toDoPredefinedMapper.mapListToForm(toDoPredefinedRepo.findAll());
  }

  @Override
  public List<ToDoPredefinedSimpleForm> findAllSimple() {
    return toDoPredefinedSimpleMapper.mapToFromList(toDoPredefinedRepo.findAll());
  }

  @Override
  public List<ToDoPredefinedForm> findAllAndSortByName() {
    return toDoPredefinedRepo.findAllByOrderByNameAsc().stream()
        .map(toDoPredefinedMapper::personToDoPredefinedMapperDto)
        .collect(Collectors.toList());
  }

  @Override
  public ToDoPredefinedForm findById(final Long id) {
    return toDoPredefinedMapper.personToDoPredefinedMapperDto(
        toDoPredefinedRepo.findById(id).get());
  }

  @Override
  public ToDoPredefinedSimpleForm findByIdSimple(final Long id) {
    return toDoPredefinedSimpleMapper.mapToFrom(toDoPredefinedRepo.findById(id).get());
  }

  @Override
  public List<ToDoPredefinedForm> findAllByNameNotIn(final List<String> names) {
    return toDoPredefinedMapper.mapListToForm(toDoPredefinedRepo.findAllByNameNotIn(names));
  }

  @Override
  public List<ToDoPredefinedSimpleForm> findAllByNameNotInSimple(final List<String> names) {
    return toDoPredefinedSimpleMapper.mapToFromList(toDoPredefinedRepo.findAllByNameNotIn(names));
  }
}
