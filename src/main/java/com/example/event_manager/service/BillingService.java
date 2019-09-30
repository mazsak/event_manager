package com.example.event_manager.service;

import com.example.event_manager.form.BillingForm;
import java.util.List;

public interface BillingService extends BasicService<BillingForm, Long> {

  void changeState(Long id);

  List<BillingForm> getAvailableBillings(List<BillingForm> old);

  List<BillingForm> getDeletedBillings(List<BillingForm> old);
}
