package com.example.event_manager.service;

import com.example.event_manager.form.BillingFilter;
import com.example.event_manager.form.BillingForm;
import java.util.List;

public interface BillingFilterService {

  BillingFilter prepareBillingFilter(List<BillingForm> billingForms, Long eventId);

}
