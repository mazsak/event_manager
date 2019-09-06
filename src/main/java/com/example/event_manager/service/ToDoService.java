package com.example.event_manager.service;

import com.example.event_manager.model.ToDo;

import java.util.List;

public interface ToDoService {
  boolean save(ToDo toDo);

  List<ToDo> saveAllAndGet(List<ToDo> toDos);

  void delete(Long id);

  List<ToDo> findAll();

  ToDo findById(Long id);
}
