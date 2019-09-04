package com.example.event_manager.service;

import com.example.event_manager.model.ToDo;
import com.example.event_manager.repo.IToDoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService implements IToDoService {

  @Autowired private IToDoRepo toDoRepo;

  @Override
  public boolean save(ToDo toDo) {
    ToDo save = toDoRepo.save(toDo);
    return save != null;
  }

  @Override
  public void delete(Long id) {
    toDoRepo.deleteById(id);
  }

  @Override
  public List<ToDo> findAll() {
    return toDoRepo.findAll();
  }

  @Override
  public ToDo findById(Long id) {
    return toDoRepo.findById(id).get();
  }

  @Override
  public List<ToDo> findByPredefined(boolean predefined) {
    return toDoRepo.findByPredefined(predefined);
  }
}
