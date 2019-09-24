package com.example.event_manager.controller;

import com.example.event_manager.form.BillingForm;
import com.example.event_manager.form.EventAddEditForm;
import com.example.event_manager.form.EventDetailsForm;
import com.example.event_manager.form.EventForm;
import com.example.event_manager.form.TaskStatusForm;
import com.example.event_manager.form.ToDoPredefinedForm;
import com.example.event_manager.form.ToDoPredefinedSimpleForm;
import com.example.event_manager.model.BillingRaportSchema;
import com.example.event_manager.model.BillingsSummary;
import com.example.event_manager.model.Event;
import com.example.event_manager.service.BillingRaportService;
import com.example.event_manager.service.BillingService;
import com.example.event_manager.service.EventService;
import com.example.event_manager.service.PersonService;
import com.example.event_manager.service.TaskStatusService;
import com.example.event_manager.service.ToDoPredefinedService;
import lombok.AllArgsConstructor;
import org.apache.fop.apps.FOPException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("events")
@Controller
@AllArgsConstructor
public class EventController {

  private final PersonService personService;
  private final EventService eventService;
  private final TaskStatusService taskStatusService;
  private final BillingRaportService billingRaportService;
  private final ToDoPredefinedService toDoPredefinedService;
  private final BillingService billingService;

  @GetMapping(value = "billingsReport", produces = MediaType.APPLICATION_PDF_VALUE)
  public ResponseEntity<byte[]> billings(@RequestParam final Long id)
      throws TransformerException, IOException, FOPException {
    BillingRaportSchema brs = eventService.generateBillingRaportSchemaForEvent(id);
    final byte[] pdfInByteArray = billingRaportService.convertBillingRaportSchemaToByteStream(brs);
    return new ResponseEntity<>(pdfInByteArray, HttpStatus.OK);
  }

  @GetMapping("/details/{id}/delete")
  public String eventDelete(@PathVariable final Long id) {
    eventService.delete(id);

    return "redirect:/events/all";
  }

  @GetMapping("/started/{id}")
  public String eventChangeStarted(@PathVariable final Long id) {
    eventService.changeStarted(id);

    return "redirect:/events/details/" + id;
  }

  @GetMapping("/details/{id}")
  public String eventDetails(final Model model, @PathVariable final Long id) {
    final EventForm event = eventService.eventFormById(id);
    event.separationTasksOnList();
    event.eventOutDatedCheck();

    model.addAttribute(
            "eventDetails",
            EventDetailsForm.builder()
                    .event(event)
                    .summary(new BillingsSummary(event.getBillings()))
                    .build());

    return "/event/details";
  }

  @GetMapping("/details/{id}/status")
  public String eventDetailsChangeTaskStatus(
      final Model model,
      @PathVariable final Long id,
      @RequestParam(value = "index", required = false) final String index,
      @RequestParam(value = "element", required = false) final String element) {
    if (element.equals("billing")) billingService.changeState(Long.valueOf(index));
    else taskStatusService.changeState(Long.valueOf(index));

    return "redirect:/events/details/" + id;
  }

  @GetMapping("/add")
  public String add(final Model model) {
    model.addAttribute(
        "eventAddEditForm",
        EventAddEditForm.builder()
            .event(
                EventForm.builder()
                    .started(false)
                    .taskStatus(
                        TaskStatusForm.builder().status(false).taskStatusType("adhoc").build())
                    .billing(BillingForm.builder().build())
                    .build())
            .predefinedNameList(toDoPredefinedService.findAllSimple())
            .people(personService.findAll())
            .position(0)
            .collapses(Arrays.asList(true, false, false, false, false, false))
            .build());

    return "event/add";
  }

  @GetMapping("/add/{id}")
  public String edit(final Model model, @PathVariable final Long id) {
    final EventForm event = eventService.eventFormById(id);
    event.separationTasksOnList();
    if (event.getPredefinedList() == null) {
      event.setPredefinedList(new ArrayList<>());
    }

    List<ToDoPredefinedSimpleForm> predefinedSimpleForms;
    if (event.getPredefinedList().isEmpty()) {
      predefinedSimpleForms = toDoPredefinedService.findAllSimple();
    } else {
      predefinedSimpleForms =
          toDoPredefinedService.findAllByNameNotInSimple(
                  event.getPredefinedList().stream()
                  .map(e -> e.get(0).getTaskStatusType())
                  .collect(Collectors.toList()));
    }

    model.addAttribute(
        "eventAddEditForm",
        EventAddEditForm.builder()
            .event(event)
            .predefinedNameList(predefinedSimpleForms)
            .people(personService.findAll())
            .position(0)
            .collapses(Arrays.asList(true, false, false, false, false, false))
            .build());

    return "/event/add";
  }

  @PostMapping(value = "/add", params = "action=addAdhoc")
  public String addAdhoc(final Model model, @ModelAttribute EventAddEditForm eventAddEditForm) {
    if (eventAddEditForm.getEvent().getTaskStatuses() == null) {
      eventAddEditForm.getEvent().setTaskStatuses(new ArrayList<>());
    }
    eventAddEditForm
        .getEvent()
        .getTaskStatuses()
        .add(TaskStatusForm.builder().status(false).taskStatusType("adhoc").build());
    eventAddEditForm = updateData(eventAddEditForm);

    model.addAttribute("eventAddEditForm", eventAddEditForm);

    return "event/add";
  }

  @PostMapping(value = "/add", params = "removeAdhoc")
  public String removeAdhoc(
      final Model model,
      @ModelAttribute EventAddEditForm eventAddEditForm,
      @RequestParam("removeAdhoc") final String indexAdhoc) {
    TaskStatusForm taskStatusRemove =
        eventAddEditForm.getEvent().getTaskStatuses().get(Integer.parseInt(indexAdhoc));
    eventAddEditForm.getEvent().getTaskStatuses().remove(taskStatusRemove);
    eventAddEditForm = updateData(eventAddEditForm);

    model.addAttribute("eventAddEditForm", eventAddEditForm);

    return "event/add";
  }

  @PostMapping(value = "/add", params = "action=addBilling")
  public String addBilling(final Model model, @ModelAttribute EventAddEditForm eventAddEditForm) {
    if (eventAddEditForm.getEvent().getBillings() == null) {
      eventAddEditForm.getEvent().setBillings(new ArrayList<>());
    }
    eventAddEditForm.getEvent().getBillings().add(BillingForm.builder().build());
    eventAddEditForm = updateData(eventAddEditForm);

    model.addAttribute("eventAddEditForm", eventAddEditForm);

    return "event/add";
  }

  @PostMapping(value = "/add", params = "removeBilling")
  public String removeBilling(
      final Model model,
      @ModelAttribute EventAddEditForm eventAddEditForm,
      @RequestParam("removeBilling") final String indexBilling) {
    BillingForm billingRemove =
        eventAddEditForm.getEvent().getBillings().get(Integer.parseInt(indexBilling));
    eventAddEditForm.getEvent().getBillings().remove(billingRemove);
    eventAddEditForm = updateData(eventAddEditForm);

    model.addAttribute("eventAddEditForm", eventAddEditForm);

    return "event/add";
  }

  @PostMapping(value = "/add", params = "addPredefined")
  public String addPredefined(
      final Model model,
      @RequestParam("addPredefined") final ToDoPredefinedSimpleForm predefined,
      @ModelAttribute EventAddEditForm eventAddEditForm) {
    final ToDoPredefinedForm predefinedForm = toDoPredefinedService.findById(predefined.getId());
    if (!predefinedForm.getTasks().isEmpty()) {
      eventAddEditForm.getEvent().addToDoPredefined(predefinedForm);
    }
    eventAddEditForm = updateData(eventAddEditForm);

    model.addAttribute("eventAddEditForm", eventAddEditForm);

    return "event/add";
  }

  @PostMapping(value = "/add", params = "removePredefined")
  public String removePredefined(
      final Model model,
      @RequestParam("removePredefined") final String indexPredefined,
      @ModelAttribute EventAddEditForm eventAddEditForm) {
    List<TaskStatusForm> predefinedRemove =
            eventAddEditForm.getEvent().getPredefinedList().get(Integer.parseInt(indexPredefined));
    eventAddEditForm.getEvent().getPredefinedList().remove(predefinedRemove);
    eventAddEditForm = updateData(eventAddEditForm);

    model.addAttribute("eventAddEditForm", eventAddEditForm);
    return "event/add";
  }

  @PostMapping(value = "add", params = "action=save")
  public String save(
      @ModelAttribute("eventAddEditForm") @Valid EventAddEditForm eventAddEditForm,
      BindingResult bindingResult,
      Model model) {
    if (bindingResult.hasErrors()) {
      eventAddEditForm = updateData(eventAddEditForm);

      model.addAttribute("eventAddEditForm", eventAddEditForm);

      return "event/add";
    } else {
      eventAddEditForm.getEvent().makeToOneList();
      eventService.saveEventForm(eventAddEditForm.getEvent());
      return "redirect:/events/all";
    }
  }

  @GetMapping(value = "all")
  public ModelAndView eventList() {
    final ModelAndView mv = new ModelAndView("event/list");

    final Map<String, List<Event>> nameToListMap = this.eventService.getEventsPartition();

    mv.addObject("notstarted", nameToListMap.get("notstarted"));
    mv.addObject("started", nameToListMap.get("started"));
    mv.addObject("outdated", nameToListMap.get("outdated"));
    return mv;
  }

  @GetMapping(value = "all", params = "search")
  public String search(
      @RequestParam(value = "query", required = false) final String query, final Model model) {

    final Map<String, List<Event>> nameToListMap = this.eventService.searchByNamePlaceTopic(query);

    model.addAttribute("notstarted", nameToListMap.get("notstrated"));
    model.addAttribute("started", nameToListMap.get("started"));
    model.addAttribute("outdated", nameToListMap.get("outdated"));

    return "event/list";
  }

  private EventAddEditForm updateData(EventAddEditForm eventAddEditForm) {
    eventAddEditForm.setPeople(personService.findAll());
    if (CollectionUtils.isEmpty(eventAddEditForm.getEvent().getPredefinedList())) {
      eventAddEditForm.setPredefinedNameList(toDoPredefinedService.findAllSimple());
    } else {
      eventAddEditForm.setPredefinedNameList(
          toDoPredefinedService.findAllByNameNotInSimple(
                  eventAddEditForm.getEvent().getPredefinedList().stream()
                  .map(x -> x.get(0).getTaskStatusType())
                  .collect(Collectors.toList())));
    }

    return eventAddEditForm;
  }
}
