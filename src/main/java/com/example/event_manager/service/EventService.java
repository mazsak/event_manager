package com.example.event_manager.service;

import com.example.event_manager.form.AllEventsForm;
import com.example.event_manager.form.EventForm;
import com.example.event_manager.model.BillingRaportSchema;
import org.apache.fop.apps.FOPException;
import org.springframework.data.domain.Pageable;

import javax.xml.transform.TransformerException;
import java.io.IOException;

public interface EventService extends BasicService<EventForm, Long> {

  AllEventsForm searchByNamePlaceTopic(
          String query, Pageable pagingS, Pageable pagingN, Pageable pagingO);

  BillingRaportSchema generateBillingReportSchemaForEvent(final Long id)
      throws TransformerException, IOException, FOPException;

  void changeStarted(Long id);

  AllEventsForm getPartition(Pageable pagingS, Pageable pagingN, Pageable pagingO);
}
