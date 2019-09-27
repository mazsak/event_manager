package com.example.event_manager.controller;

import com.example.event_manager.form.AccountForm;
import com.example.event_manager.form.BillingFilter;
import com.example.event_manager.form.BillingForm;
import com.example.event_manager.form.EventForm;
import com.example.event_manager.service.BillingReportService;
import com.example.event_manager.service.EventService;
import com.example.event_manager.service.PersonService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("billings")
@Controller
@Component

public class BillingController {

  @Value("${report.filename}")
  private String filename;

  private final EventService eventService;
  private final PersonService personService;
  private final BillingReportService billingReportService;

  public BillingController(EventService eventService, PersonService personService,
      BillingReportService billingReportService) {
    this.eventService = eventService;
    this.personService = personService;
    this.billingReportService = billingReportService;
  }

  @GetMapping("/details/{id}")
  public String billingDetails(final Model model, @PathVariable final Long id) {

    EventForm ef = eventService.findById(id);
    AccountForm accountForm = new AccountForm();
    List<BillingForm> billingForms = new ArrayList<>(ef.getBillings());
    Collections.sort(billingForms);
    BillingFilter billingFilter = new BillingFilter();
    billingFilter.setEventId(id);
    billingFilter.setDateCreationStart(billingForms.get(0).getDateOfCreation());
    billingFilter.setDateCreationEnd(billingForms.get(billingForms.size() - 1).getDateOfCreation());
    accountForm.setBillingFormList(ef.getBillings());
    accountForm.setEventId(id);
    model.addAttribute("accountForm", accountForm);
    model.addAttribute("persons", personService.findAll());
    model.addAttribute("billingFilter", billingFilter);

    return "billingsDetails";
  }

  @GetMapping("/details/delete")
  public String deleteBillingFromEvent(@RequestParam("eventId") final Long eventId,
      @RequestParam("billingId") Long billingId) {

    return "details/delete";
  }


  @PostMapping("/details/report/filter")
  public ResponseEntity<byte[]> getFilterObject(final Model model,
      @ModelAttribute("billingFilter") final BillingFilter billingFilter)
      throws IOException {
    EventForm ef = eventService.findById(billingFilter.getEventId());
    List<BillingForm> filteredBillings = billingReportService
        .filterBillingFormList(ef.getBillings(), billingFilter);
    HttpHeaders headers = new HttpHeaders();
    ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
        .filename(filename)
        .build();
    headers.setContentDisposition(contentDisposition);
    final byte[] excelInByteArray = billingReportService.generateSheetForBillings(filteredBillings);
    return ResponseEntity.ok().headers(headers).body(excelInByteArray);
  }


}
