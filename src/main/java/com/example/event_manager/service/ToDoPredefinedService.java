package com.example.event_manager.service;

import com.example.event_manager.model.ToDoPredefined;

import java.util.List;

public interface ToDoPredefinedService {
  boolean save(ToDoPredefined toDoPredefined);

  void delete(Long id);

  List<ToDoPredefined> findAll();

  ToDoPredefined findById(Long id);
}
