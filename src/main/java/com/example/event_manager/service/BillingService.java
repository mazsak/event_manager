package com.example.event_manager.service;

import com.example.event_manager.form.BillingForm;

public interface BillingService extends BasicService<BillingForm, Long> {
  void changeState(Long id);
}
