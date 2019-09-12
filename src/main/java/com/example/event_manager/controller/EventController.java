package com.example.event_manager.controller;

import com.example.event_manager.form.BillingForm;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.xml.transform.TransformerException;
import lombok.AllArgsConstructor;
import org.apache.fop.apps.FOPException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("events")
@Controller
@AllArgsConstructor()
public class EventController {

  private final PersonService personService;
  private final EventService eventService;
  private final TaskStatusService taskStatusService;
  private final BillingRaportService billingRaportService;
  private final ToDoPredefinedService toDoPredefinedService;
  private final BillingService billingService;


  @GetMapping(value = "billingsRaport", produces = "application/pdf")
  public ResponseEntity<byte[]> billings(@RequestParam final Long id)
      throws TransformerException, IOException, FOPException {
    BillingRaportSchema brs = eventService.generateBillingRaportSchemaForEvent(id);
    final byte[] pdfInByteArray = billingRaportService.convertBillingRaportSchemaToByteStream(brs);
    return new ResponseEntity<>(pdfInByteArray, HttpStatus.OK);
  }

  @GetMapping("details")
  public String detailsEvent(final Model model, @RequestParam final Long id) {
    final EventForm event = eventService.eventFormById(id);
    final Map<String, List<TaskStatusForm>> map = eventService.preapreTasksForEvent(event);
    model.addAttribute("tasks", map);
    model.addAttribute("event", event);
    model.addAttribute("billings", event.getBillings());
    model.addAttribute("billingsSummary", new BillingsSummary(event.getBillings()));
    return "/event/details";
  }

  @GetMapping(value = "deleteTaskFromEvent")
  public String deleteTaskFromEvent(
      final Model model, @RequestParam final Long taskId, @RequestParam final Long eventId) {
    eventService.deleteTaskStatusFromEvent(taskId, eventId);
    return "redirect:/events/add/" + eventId;
  }

  @GetMapping(value = "deleteBillingFromEvent")
  public String deleteBillingFromEvent(
      final Model model, @RequestParam final Long billingId, @RequestParam final Long eventId) {
    eventService.deleteBillingFromEvent(billingId, eventId);
    return "redirect:/events/add/" + eventId;
  }

  @GetMapping("add")
  public String add(final Model model) {
    model.addAttribute(
        "event",
        EventForm.builder()
            .started(false)
            .position(0)
            .collapses(Arrays.asList(true, false, false, false, false, false))
            .taskStatus(TaskStatusForm.builder().status(false).taskStatusType("adhoc").build())
            .billing(BillingForm.builder().build())
            .build());
    model.addAttribute("predefinedList", toDoPredefinedService.findAllSimple());
    model.addAttribute("people", personService.findAll());
    return "event/add";
  }

  @GetMapping("/details/{id}")
  public String eventDetails(final Model model, @PathVariable final Long id) {
    final EventForm event = eventService.eventFormById(id);
    event.separationTasksOnList();
    event.setCollapses(Arrays.asList(true, false, false, false, false, false));
    event.setPosition(0);

    model.addAttribute("event", event);
    if (event.getPredefineds() == null) {
      event.setPredefineds(new ArrayList<>());
    }
    if (event.getPredefineds().isEmpty()) {
      model.addAttribute("predefinedList", toDoPredefinedService.findAllSimple());
    } else {
      model.addAttribute(
          "predefinedList",
          toDoPredefinedService.findAllByNameNotInSimple(
              event.getPredefineds().stream()
                  .map(e -> e.get(0).getTaskStatusType())
                  .collect(Collectors.toList())));
    }
    model.addAttribute("people", personService.findAll());
    return "/event/details";
  }
  @GetMapping("/add/{id}")
  public String edit(final Model model, @PathVariable final Long id) {
    final EventForm event = eventService.eventFormById(id);
    event.separationTasksOnList();
    event.setCollapses(Arrays.asList(true, false, false, false, false, false));
    event.setPosition(0);

    model.addAttribute("event", event);
    if (event.getPredefineds() == null) {
      event.setPredefineds(new ArrayList<>());
    }
    if (event.getPredefineds().isEmpty()) {
      model.addAttribute("predefinedList", toDoPredefinedService.findAllSimple());
    } else {
      model.addAttribute(
          "predefinedList",
          toDoPredefinedService.findAllByNameNotInSimple(
              event.getPredefineds().stream()
                  .map(e -> e.get(0).getTaskStatusType())
                  .collect(Collectors.toList())));
    }
    model.addAttribute("people", personService.findAll());
    return "/event/add";
  }

  @PostMapping(value = "add", params = "action=addAdhoc")
  public String addTask(final Model model, @ModelAttribute final EventForm event) {
    if (event.getTaskStatuses() == null) {
      event.setTaskStatuses(new ArrayList<>());
    }
    event
        .getTaskStatuses()
        .add(TaskStatusForm.builder().status(false).taskStatusType("adhoc").build());
    model.addAttribute("event", event);
    if (event.getPredefineds() == null) {
      event.setPredefineds(new ArrayList<>());
    }
    if (event.getPredefineds().isEmpty()) {
      model.addAttribute("predefinedList", toDoPredefinedService.findAllSimple());
    } else {
      model.addAttribute(
          "predefinedList",
          toDoPredefinedService.findAllByNameNotInSimple(
              event.getPredefineds().stream()
                  .map(e -> e.get(0).getTaskStatusType())
                  .collect(Collectors.toList())));
    }
    model.addAttribute("people", personService.findAll());
    return "event/add";
  }

  @PostMapping(value = "add", params = "action=addBilling")
  public String addBilling(final Model model, @ModelAttribute final EventForm event) {
    if (event.getTaskStatuses() == null) {
      event.setTaskStatuses(new ArrayList<>());
    }
    event
        .getBillings()
        .add(BillingForm.builder().build());
    model.addAttribute("event", event);
    if (event.getPredefineds() == null) {
      event.setPredefineds(new ArrayList<>());
    }
    if (event.getPredefineds().isEmpty()) {
      model.addAttribute("predefinedList", toDoPredefinedService.findAllSimple());
    } else {
      model.addAttribute(
          "predefinedList",
          toDoPredefinedService.findAllByNameNotInSimple(
              event.getPredefineds().stream()
                  .map(e -> e.get(0).getTaskStatusType())
                  .collect(Collectors.toList())));
    }
    model.addAttribute("people", personService.findAll());
    return "event/add";
  }


  @PostMapping(value = "add", params = "predefined")
  public String addPredefined(
      final Model model,
      @RequestParam("predefined") final ToDoPredefinedSimpleForm predefined,
      @ModelAttribute final EventForm event) {
    final ToDoPredefinedForm predefinedForm = toDoPredefinedService.findById(predefined.getId());
    if (!predefinedForm.getTasks().isEmpty()) {
      event.addToDoPredefined(predefinedForm);
    }

    model.addAttribute("event", event);
    model.addAttribute(
        "predefinedList",
        toDoPredefinedService.findAllByNameNotInSimple(
            event.getPredefineds().stream()
                .map(e -> e.get(0).getTaskStatusType())
                .collect(Collectors.toList())));
    model.addAttribute("people", personService.findAll());
    return "event/add";
  }

  @PostMapping(value = "add", params = "predefined_remove")
  public String removePredefined(
      final Model model,
      @RequestParam("predefined_remove") final String indexPredefined,
      @ModelAttribute final EventForm event) {

    event.getPredefineds().remove(Integer.parseInt(indexPredefined));

    model.addAttribute("event", event);
    if (event.getPredefineds() == null) {
      event.setPredefineds(new ArrayList<>());
    }
    if (event.getPredefineds().isEmpty()) {
      model.addAttribute("predefinedList", toDoPredefinedService.findAllSimple());
    } else {
      model.addAttribute(
          "predefinedList",
          toDoPredefinedService.findAllByNameNotInSimple(
              event.getPredefineds().stream()
                  .map(e -> e.get(0).getTaskStatusType())
                  .collect(Collectors.toList())));
    }
    model.addAttribute("people", personService.findAll());
    return "event/add";
  }

  @PostMapping(value = "add", params = "action=save")
  public String save(@ModelAttribute final EventForm event) {
    event.makeToOneList();
    eventService.saveEventForm(event);
    return "redirect:/events/all";
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
  public String search(@RequestParam(value = "query", required = false) final String query,
      final Model model) {

    final Map<String, List<Event>> nameToListMap = this.eventService.searchByNamePlaceTopic(query);

    model.addAttribute("notstarted", nameToListMap.get("notstrated"));
    model.addAttribute("started", nameToListMap.get("started"));
    model.addAttribute("outdated", nameToListMap.get("outdated"));

    return "event/list";
  }

}
