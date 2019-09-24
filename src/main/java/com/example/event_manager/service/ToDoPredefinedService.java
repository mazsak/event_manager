package com.example.event_manager.service;

import com.example.event_manager.form.ToDoPredefinedForm;
import com.example.event_manager.form.ToDoPredefinedSimpleForm;
import java.util.List;

public interface ToDoPredefinedService extends BasicService<ToDoPredefinedForm, Long> {

  List<ToDoPredefinedForm> findAllAndSortByName();

  List<ToDoPredefinedSimpleForm> findAllSimple();

  ToDoPredefinedSimpleForm findByIdSimple(final Long id);

  List<ToDoPredefinedSimpleForm> findAllByNameNotInSimple(final List<String> names);
}
