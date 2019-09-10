package com.example.event_manager.service;

import com.example.event_manager.model.ToDoPredefined;
import com.example.event_manager.repo.ToDoPredefinedRepo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToDoPredefinedServiceImpl implements ToDoPredefinedService {

  private final ToDoPredefinedRepo toDoPredefinedRepo;

  @Override
  public boolean save(final ToDoPredefined toDoPredefined) {
    return this.toDoPredefinedRepo.save(toDoPredefined) != null;
  }

  @Override
  public void delete(final Long id) {
    this.toDoPredefinedRepo.deleteById(id);
  }

  @Override
  public List<ToDoPredefined> findAll() {
    return this.toDoPredefinedRepo.findAll();
  }

  @Override
  public ToDoPredefined findById(final Long id) {
    return this.toDoPredefinedRepo.findById(id).get();
  }
}
