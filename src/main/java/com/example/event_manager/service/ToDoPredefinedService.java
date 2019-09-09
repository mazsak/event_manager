package com.example.event_manager.service;

import com.example.event_manager.model.ToDoPredefined;
import java.util.List;

public interface ToDoPredefinedService {

  boolean save(final ToDoPredefined toDoPredefined);

  void delete(final Long id);

  List<ToDoPredefined> findAll();

  ToDoPredefined findById(final Long id);
}
