package com.example.event_manager.service;

import com.example.event_manager.form.ToDoPredefinedForm;

import java.util.List;

public interface ToDoPredefinedService {

  boolean save(final ToDoPredefinedForm toDoPredefined);

  void delete(final Long id);

  List<ToDoPredefinedForm> findAll();

  ToDoPredefinedForm findById(final Long id);
}
