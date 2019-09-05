package com.example.event_manager.service;

import com.example.event_manager.model.Task;

import java.util.List;

public interface TaskService {
  boolean save(Task task);

  void delete(Long id);

  List<Task> findAll();

  Task findById(Long id);
}
