package com.example.event_manager.service;

import com.example.event_manager.model.ToDo;

import java.util.List;

public interface IToDoService {
  boolean save(ToDo toDo);

  void delete(Long id);

  List<ToDo> findAll();

  ToDo findById(Long id);

  List<ToDo> findByPredefined(boolean predefined);
}
