package com.example.event_manager.service;

import com.example.event_manager.form.ToDoPredefinedForm;
import com.example.event_manager.form.ToDoPredefinedSimpleForm;
import com.example.event_manager.mapper.ToDoPredefinedMapper;
import com.example.event_manager.mapper.ToDoPredefinedSimpleMapper;
import com.example.event_manager.model.ToDoPredefined;
import com.example.event_manager.repo.ToDoPredefinedRepo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ToDoPredefinedServiceImpl
    extends BasicServiceImpI<
    ToDoPredefined, ToDoPredefinedForm, ToDoPredefinedRepo, ToDoPredefinedMapper, Long>
    implements ToDoPredefinedService {

  private final ToDoPredefinedSimpleMapper toDoPredefinedSimpleMapper;

  public ToDoPredefinedServiceImpl(
      final ToDoPredefinedRepo toDoPredefinedRepo,
      final ToDoPredefinedMapper mapper,
      final ToDoPredefinedSimpleMapper toDoPredefinedSimpleMapper) {
    super(toDoPredefinedRepo, mapper);
    this.toDoPredefinedSimpleMapper = toDoPredefinedSimpleMapper;
  }

  @Override
  public List<ToDoPredefinedSimpleForm> findAllSimple() {
    return toDoPredefinedSimpleMapper.mapToDTOList(repo.findAll());
  }

  @Override
  public List<ToDoPredefinedForm> findAllAndSortByName() {
    return repo.findAllByOrderByNameAsc().stream()
        .map(mapper::mapToDTO)
        .collect(Collectors.toList());
  }

  @Override
  public ToDoPredefinedSimpleForm findByIdSimple(final Long id) {
    return toDoPredefinedSimpleMapper.mapToDTO(repo.findById(id).get());
  }

  @Override
  public List<ToDoPredefinedSimpleForm> findAllByNameNotInSimple(final List<String> names) {
    return toDoPredefinedSimpleMapper.mapToDTOList(repo.findAllByNameNotIn(names));
  }
}
