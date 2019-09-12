package com.example.event_manager.service;

import com.example.event_manager.form.ToDoPredefinedForm;
import com.example.event_manager.form.ToDoPredefinedSimpleForm;
import java.util.List;

public interface ToDoPredefinedService {

  void save(final ToDoPredefinedForm toDoPredefined);

  void delete(final Long id);

  List<ToDoPredefinedForm> findAll();

  List<ToDoPredefinedSimpleForm> findAllSimple();

  List<ToDoPredefinedForm> findAllAndSortByName();

  ToDoPredefinedForm findById(final Long id);

  ToDoPredefinedSimpleForm findByIdSimple(final Long id);

  List<ToDoPredefinedForm> findAllByNameNotIn(List<String> names);

  List<ToDoPredefinedSimpleForm> findAllByNameNotInSimple(final List<String> names);
}
