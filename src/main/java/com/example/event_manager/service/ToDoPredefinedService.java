package com.example.event_manager.service;

import com.example.event_manager.form.ToDoPredefinedForm;
import com.example.event_manager.model.ToDoPredefined;

import java.util.List;

public interface ToDoPredefinedService {
  boolean save(ToDoPredefinedForm toDoPredefinedForm);

  void delete(Long id);

  List<ToDoPredefinedForm> findAll();

  ToDoPredefinedForm findById(Long id);
}
