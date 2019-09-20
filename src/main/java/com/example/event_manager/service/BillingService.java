package com.example.event_manager.service;

import com.example.event_manager.form.BillingForm;
import com.example.event_manager.model.Billing;
import java.util.List;

public interface BillingService {

  boolean save(final BillingForm billing);

  BillingForm saveAndReturn(BillingForm billingForm);

  void delete(final Long id);

  List<BillingForm> findAll();

  void changeState(Long id);

  Billing findById(final Long id);

  BillingForm billingFormById(final Long id);

}
