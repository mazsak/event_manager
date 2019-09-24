package com.example.event_manager.service;

import com.example.event_manager.form.BillingFilter;
import com.example.event_manager.form.BillingForm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BillingFilterServiceImpl implements BillingFilterService {

  public BillingFilter prepareBillingFilter(List<BillingForm> billingForms, Long eventId) {
    if (billingForms == null) {
      billingForms = new ArrayList<>();
    }
    List<BillingForm> list = new ArrayList<>(billingForms);
    Collections.sort(list);
    BillingFilter billingFilter = new BillingFilter();
    billingFilter.setEventId(eventId);
    if (billingForms.size() == 0) {
      billingFilter.setDateCreationStart(null);
      billingFilter.setDateCreationEnd(null);
    } else {
      billingFilter.setDateCreationStart(list.get(0).getDateOfCreation());
      billingFilter.setDateCreationEnd(list.get(list.size() - 1).getDateOfCreation());
    }
    return billingFilter;
  }

}
