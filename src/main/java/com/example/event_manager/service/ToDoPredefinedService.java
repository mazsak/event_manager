package com.example.event_manager.service;

import com.example.event_manager.form.ToDoPredefinedForm;
import com.example.event_manager.form.ToDoPredefinedSimpleForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ToDoPredefinedService extends BasicService<ToDoPredefinedForm, Long> {

  List<ToDoPredefinedForm> findAllAndSortByName();

  Page<ToDoPredefinedForm> findAllAndSortByName(Pageable pageable);

  List<ToDoPredefinedSimpleForm> findAllSimple();

  ToDoPredefinedSimpleForm findByIdSimple(final Long id);

  List<ToDoPredefinedSimpleForm> findAllByNameNotInSimple(final List<String> names);
}
