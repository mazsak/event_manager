package com.example.event_manager.service;

import com.example.event_manager.form.AllEventsForm;
import com.example.event_manager.form.EventForm;
import org.springframework.data.domain.Pageable;

public interface EventService extends BasicService<EventForm, Long> {

  AllEventsForm searchByNamePlaceTopic(
      String query, Pageable pagingS, Pageable pagingN, Pageable pagingO);

  void changeStarted(Long id);

  AllEventsForm getPartition(Pageable pagingS, Pageable pagingN, Pageable pagingO);
}
