package com.example.event_manager.controller;

import com.example.event_manager.form.BillingForm;
import com.example.event_manager.form.EventForm;
import com.example.event_manager.form.TaskStatusForm;
import com.example.event_manager.model.Event;
import com.example.event_manager.service.BillingService;
import com.example.event_manager.service.EventService;
import com.example.event_manager.service.PersonService;
import com.example.event_manager.service.TaskStatusService;
import com.example.event_manager.utils.BillingsSummary;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.xml.transform.TransformerException;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.fop.apps.FOPException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor()
@RequestMapping("events")
public class EventController {

  private final PersonService personService;
  private final EventService eventService;
  private final TaskStatusService taskStatusService;

  @GetMapping(value = "billingsRaport", produces = "application/pdf")
  public ResponseEntity<byte[]> Billings(@RequestParam final Long id)
      throws TransformerException, IOException, FOPException {
    final String pathToFile = eventService.pathToGeneratatedBillingsRaportForEvent(id);
    try (final InputStream is = new FileInputStream(pathToFile)) {
      ResponseEntity<byte[]> response = new ResponseEntity<>(IOUtils.toByteArray(is), HttpStatus.OK);
      return response;
    } catch (final IOException ex) {
      throw new RuntimeException("IOError writing file to output stream");
    }
  }


  @GetMapping("details")
  public String detailsEvent(final Model model, @RequestParam final Long id) {
    final EventForm event = eventService.eventFormById(id);
    final Map<String, List<TaskStatusForm>> map = eventService.preapreTasksForEvent(event);
    model.addAttribute("tasks", map);
    model.addAttribute("event", event);
    model.addAttribute("billings",event.getBillings());
    model.addAttribute("billingsSummary",new BillingsSummary(event.getBillings()));
    return "/event/details";
  }


  @GetMapping("addAdhocTaskToEventForm")
  public String addAdhocToEvent(final Model model, @RequestParam final Long id) {
    model.addAttribute("eventId", id);
    model.addAttribute("taskStatusForm", new TaskStatusForm());
    model.addAttribute("persons", personService.findAll());
    return "event/addAdhoc";
  }

  @PostMapping(value = "saveAdhocTaskToEvent")
  public String saveAdhocToEvent(final Model model,
      @ModelAttribute(value = "taskStatusForm") final TaskStatusForm taskStatusForm,
      @RequestParam final Long eventId) {

    eventService.saveAdhocTaskToEvent(taskStatusForm, eventId);

    return "redirect:/events/details?id=" + eventId;
  }

  @GetMapping(value = "deleteTaskFromEvent")
  public String deleteTaskFromEvent(final Model model, @RequestParam final Long taskId,
      @RequestParam final Long eventId) {
    eventService.deleteTaskStatusFromEvent(taskId, eventId);
    return "redirect:/events/details?id=" + eventId;
  }

  @GetMapping(value = "editTask")
  public String editTaskStatus(final Model model, @RequestParam final Long taskId) {
    model.addAttribute("task", taskStatusService.taskStatusFormById(taskId));
    model.addAttribute("persons", personService.findAll());
    return "event/editTaskStatus";
  }

  @PostMapping(value = "editTask/save")
  public String saveEditedTask(@ModelAttribute(value = "task") final TaskStatusForm taskStatus) {
    taskStatusService.update((taskStatus));
    return "redirect:/events/details?id=" + Long.valueOf(1);
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
