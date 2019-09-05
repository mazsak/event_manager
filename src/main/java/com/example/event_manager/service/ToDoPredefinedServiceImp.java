package com.example.event_manager.service;

import com.example.event_manager.model.ToDo;
import com.example.event_manager.model.ToDoPredefined;
import com.example.event_manager.repo.ToDoPredefinedRepo;
import com.example.event_manager.repo.ToDoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoPredefinedServiceImp implements ToDoPredefinedService {

  @Autowired private ToDoPredefinedRepo toDoPredefinedRepo;

  @Override
  public boolean save(ToDoPredefined toDoPredefined) {
    ToDoPredefined save = toDoPredefinedRepo.save(toDoPredefined);
    return save != null;
  }

  @Override
  public void delete(Long id) {
    toDoPredefinedRepo.deleteById(id);
  }

  @Override
  public List<ToDoPredefined> findAll() {
    return toDoPredefinedRepo.findAll();
  }

  @Override
  public ToDoPredefined findById(Long id) {
    return toDoPredefinedRepo.findById(id).get();
  }
}
