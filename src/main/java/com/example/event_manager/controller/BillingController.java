package com.example.event_manager.controller;

import com.example.event_manager.exception.InvalidRequestException;
import com.example.event_manager.form.BillingDetailsForm;
import com.example.event_manager.form.BillingFilter;
import com.example.event_manager.form.BillingForm;
import com.example.event_manager.form.EventForm;
import com.example.event_manager.mapper.BillingMapper;
import com.example.event_manager.model.BillingType;
import com.example.event_manager.service.BillingFilterService;
import com.example.event_manager.service.BillingReportService;
import com.example.event_manager.service.BillingService;
import com.example.event_manager.service.EventService;
import com.example.event_manager.service.PersonService;
import com.example.event_manager.service.UserService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
public class BillingController {


  @Value("${report.filename}")
  private String filename;

  private final EventService eventService;
  private final PersonService personService;
  private final BillingReportService billingReportService;
  private final BillingService billingService;
  private final BillingFilterService billingFilterService;
  private final UserService userService;
  private final BillingMapper billingMapper;


  public BillingController(EventService eventService, PersonService personService,
      BillingReportService billingReportService, BillingService billingService,
      BillingFilterService billingFilterService,
      UserService userService, BillingMapper billingMapper) {
    this.eventService = eventService;
    this.personService = personService;
    this.billingReportService = billingReportService;
    this.billingService = billingService;
    this.billingFilterService = billingFilterService;
    this.userService = userService;
    this.billingMapper = billingMapper;
  }

  @GetMapping("/details/{id}")
  public String billingDetailsAvailable(final Model model,
      @PathVariable final Long id,
      @RequestParam final String type) {
    EventForm ef = eventService.findById(id);
    List<BillingForm> prepared;
    //TODO: write SQL queries for getting available/deleted billings
    if (type.equals("available")) {
      prepared = billingService.getAvailableBillings(ef.getBillings());
    } else if (type.equals("deleted")) {
      prepared = billingService.getDeletedBillings(ef.getBillings());
    } else if (type.equals("all")) {
      prepared = ef.getBillings();
    } else {
      throw new InvalidRequestException();
    }

    BillingFilter billingFilter = billingFilterService.prepareBillingFilter(prepared, id);

    BillingDetailsForm billingDetailsForm = new BillingDetailsForm();
    billingDetailsForm.setEventId(ef.getId());
    billingDetailsForm.setPeople(personService.findAll());
    billingDetailsForm.setBillingForms(prepared);
    model.addAttribute("billingDetailsForm", billingDetailsForm);
    model.addAttribute("billingFilter", billingFilter);
    model.addAttribute("listType", type);
    model.addAttribute("user", userService.getPrincipalSimple());

    return "billingsDetails";
  }

  @PostMapping("details/save")
  public String saveChanges(final Model model,
      @ModelAttribute("billingDetailsForm") final BillingDetailsForm billingDetailsForm) {

    EventForm eventForm = eventService.findById(billingDetailsForm.getEventId());

//    1- Usun z billingsDetailsForm wszystkie ktore maja odzwierciedlnenie w BD ale nie sa zedytyowane
//    2- Usun z eventu wszystkie które:
//      *są w BillingDetailsForm z id
//      i się różnią
//
    //Po tej metodzie usunę z billingDetailsForm wszystkie billingi ktore sa takie same w bazie danych
    billingDetailsForm.setBillingForms(billingDetailsForm
        .getBillingForms()
        .stream()
        .filter(billingForm->
            !eventForm.getBillings().contains(billingForm))
        .collect(Collectors.toList()));

    //W tej metodzie usunę wszystkie billingi w bazie danych ktore zostaly w BillingDetailsForms
    eventForm.deleteBillingsById(billingDetailsForm
        .getBillingForms()
        .stream()
        .map(BillingForm::getId)
        .collect(Collectors.toList()));

    billingDetailsForm
        .getBillingForms()
        .stream()
        .forEach(billingForm ->
            billingForm.setDateOfEdition(LocalDateTime.now()));
//    eventForm.setBillings(
//        eventForm.getBillings()
//        .stream()
//        .forEach(billingForm -> billingForm.setDateOfEdition(LocalDateTime.now())).
//        );

//    eventForm.setBillings(eventForm.getBillings().stream().fo);

    eventService.save(eventForm);
    eventForm.getBillings().addAll(billingDetailsForm.getBillingForms());
    eventService.save(eventForm);
    return "redirect:/billings/details/" + billingDetailsForm.getEventId() + "?type=available";
  }



  @GetMapping("/details/delete")
  public String deleteBilling(
      @RequestParam final Long billingId,
      @RequestParam final Long eventId) {
    BillingForm bf = billingService.findById(billingId);
    bf.setDeleted(true);
    billingService.save(bf);
    return "redirect:/billings/details/" + eventId + "?type=available";
  }

  @PostMapping("/details/report/filter")
  public ResponseEntity<byte[]> getFilterReportInSheet(final Model model,
      @ModelAttribute("billingFilter") final BillingFilter billingFilter,
      @RequestParam final String type)
      throws IOException {
    EventForm ef = eventService.findById(billingFilter.getEventId());
    List<BillingForm> filteredBillings = billingReportService
        .filterBillingFormList(ef.getBillings(), billingFilter);

    //TODO: write SQL queries for getting available/deleted billings
    if ("available".equals(type)) {
      filteredBillings = billingService.getAvailableBillings(filteredBillings);
    } else if ("deleted".equals(type)) {
      filteredBillings = billingService.getDeletedBillings(filteredBillings);
    }

    HttpHeaders headers = new HttpHeaders();
    ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
        .filename(filename)
        .build();
    headers.setContentDisposition(contentDisposition);
    final byte[] excelInByteArray = billingReportService.generateSheetForBillings(filteredBillings);
    return ResponseEntity.ok().headers(headers).body(excelInByteArray);
  }

  @GetMapping("/details/report/all")
  public ResponseEntity<byte[]> getFullReportInSheet(final Model model,
      @RequestParam Long eventId,
      @RequestParam String type)
      throws IOException {
    EventForm ef = eventService.findById(eventId);
    List<BillingForm> allBilings = ef.getBillings();

    if ("available".equals(type)) {
      allBilings = billingService.getAvailableBillings(allBilings);
    } else if ("deleted".equals(type)) {
      allBilings = billingService.getDeletedBillings(allBilings);
    }
    HttpHeaders headers = new HttpHeaders();
    ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
        .filename(filename)
        .build();
    headers.setContentDisposition(contentDisposition);
    final byte[] excelInByteArray = billingReportService.generateSheetForBillings(allBilings);
    return ResponseEntity.ok().headers(headers).body(excelInByteArray);
  }

  @PostMapping(value = "details/save", params = "action=addBilling")
  public String addBilling(final Model model,
      @ModelAttribute BillingDetailsForm billingDetailsForm) {
    if (billingDetailsForm.getBillingForms() == null) {
      billingDetailsForm.setBillingForms(new ArrayList<>());
    }
    billingDetailsForm.getBillingForms()
        .add(BillingForm.builder().deleted(false).confirmed(false).billingType(
            BillingType.INCOME).dateOfCreation(LocalDateTime.now())
            .dateOfEdition(LocalDateTime.now()).personAssigned(personService.findById(1L)).build());

    billingDetailsForm.setPeople(personService.findAll());
    model.addAttribute("listType", "available");
    model.addAttribute("billingDetailsForm", billingDetailsForm);
    model.addAttribute("billingFilter",
        billingFilterService.prepareBillingFilter(billingDetailsForm.getBillingForms(),
            billingDetailsForm.getEventId()));
    model.addAttribute("user", userService.getPrincipalSimple());

    return "billingsDetails";
  }


}
