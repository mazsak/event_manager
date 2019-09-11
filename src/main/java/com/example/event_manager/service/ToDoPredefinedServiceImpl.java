package com.example.event_manager.service;

import com.example.event_manager.form.ToDoPredefinedForm;
import com.example.event_manager.mapper.ToDoPredefinedMapper;
import com.example.event_manager.repo.ToDoPredefinedRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToDoPredefinedServiceImpl implements ToDoPredefinedService {

  private final ToDoPredefinedRepo toDoPredefinedRepo;
  private final ToDoPredefinedMapper toDoPredefinedMapper;

  @Override
  public boolean save(final ToDoPredefinedForm toDoPredefined) {
    return toDoPredefinedRepo.save(toDoPredefinedMapper.toPOJO(toDoPredefined)) != null;
  }

  @Override
  public void delete(final Long id) {
    toDoPredefinedRepo.deleteById(id);
  }

  @Override
  public List<ToDoPredefinedForm> findAll() {
    return toDoPredefinedRepo.findAll().stream()
        .map(toDoPredefinedMapper::personToDoPredefinedMapperDto)
        .collect(Collectors.toList());
  }

  @Override
  public ToDoPredefinedForm findById(final Long id) {
    return toDoPredefinedMapper.personToDoPredefinedMapperDto(
        toDoPredefinedRepo.findById(id).get());
  }
}
