package com.example.event_manager.controller;

import com.example.event_manager.model.Event;
import com.example.event_manager.model.Person;
import com.example.event_manager.model.TaskStatus;
import com.example.event_manager.service.EventService;
import com.example.event_manager.service.PersonService;
import com.example.event_manager.service.TaskStatusService;
import com.example.event_manager.wrapper.TaskForEventCreationWrapper;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventController {

  private PersonService personService;
  private EventService eventService;
  private TaskStatusService taskStatusService;

  @GetMapping("/")
  public String cus() {
    return "cus";
  }

  @GetMapping("/event/details")
  public String detailsEvent(Model model, @RequestParam Long id) {
    Event event = eventService.findById(id);
    Map<String, List<TaskStatus>> map = eventService.preapreTasksForEvent(event);
    model.addAttribute("tasks", map);
    model.addAttribute("event", event);
    return "/event/details";
  }

  @GetMapping("event/addAdhocTaskToEventForm")
  public String addAdhocToEvent(Model model, @RequestParam Long id) {
    TaskForEventCreationWrapper wrapper = new TaskForEventCreationWrapper(id);
    model.addAttribute("taskStatusForEventCreatingDto", wrapper);
    model.addAttribute("persons", personService.findAll());
    return "event/addAdhoc";
  }

  @PostMapping(value = "event/saveAdhocTaskToEvent")
  public String saveAdhocToEvent(Model model,
      @ModelAttribute(value = "taskStatusForEventCreatingDto") TaskForEventCreationWrapper wrapper) {
    TaskStatus ts = new TaskStatus();
    Person person = personService.findById(wrapper.getPersonId());
    ts.setTaskStatusType("adhoc");
    ts.setName(wrapper.getName());
    ts.setDate(wrapper.getDate());
    person.addTaskStatus(ts);
    Event eventToUpdate = eventService.findById(wrapper.getEventId());
    eventToUpdate.addTaskStatus(ts);
    eventService.save(eventToUpdate);
    return "redirect:/event/details?id=" + wrapper.getEventId();
  }


  @GetMapping(value = "event/deleteTaskFromEvent")
  public String deleteTaskFromEvent(Model model, @RequestParam Long taskId,
      @RequestParam Long eventId) {
    Event event = eventService.findById(eventId);
    event.removeTaskStatus(taskStatusService.findById(taskId));
    eventService.save(event);
    return "redirect:/event/details?id=" + eventId;
  }

}
