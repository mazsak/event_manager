package com.example.event_manager.service;

import com.example.event_manager.form.BillingFilter;
import com.example.event_manager.form.BillingForm;
import java.io.IOException;
import java.util.List;


public interface BillingReportService {

  List<BillingForm> filterBillingFormList(List<BillingForm> oldBillingForm,
      BillingFilter billingFilter);

  byte[] generateSheetForBillings(List<BillingForm> billingForms) throws IOException;
}
