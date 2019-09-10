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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor()
public class EventController {

  private final PersonService personService;
  private final EventService eventService;
  private final TaskStatusService taskStatusService;

  @GetMapping("/event/details")
  public String detailsEvent(final Model model, @RequestParam final Long id) {
    final Event event = eventService.findById(id);
    final Map<String, List<TaskStatus>> map = eventService.preapreTasksForEvent(event);
    model.addAttribute("tasks", map);
    model.addAttribute("event", event);
    return "/event/details";
  }

  @GetMapping("event/addAdhocTaskToEventForm")
  public String addAdhocToEvent(final Model model, @RequestParam final Long id) {
    final TaskForEventCreationWrapper wrapper = new TaskForEventCreationWrapper(id);
    model.addAttribute("taskStatusForEventCreatingDto", wrapper);
    model.addAttribute("persons", personService.findAll());
    return "event/addAdhoc";
  }

  @PostMapping(value = "event/saveAdhocTaskToEvent")
  public String saveAdhocToEvent(final Model model,
      @ModelAttribute(value = "taskStatusForEventCreatingDto") final TaskForEventCreationWrapper wrapper) {
    final TaskStatus ts = new TaskStatus();
    final Person person = personService.findById(wrapper.getPersonId());
    ts.setTaskStatusType("adhoc");
    ts.setName(wrapper.getName());
    ts.setDate(wrapper.getDate());
    person.addTaskStatus(ts);
    final Event eventToUpdate = eventService.findById(wrapper.getEventId());
    eventToUpdate.addTaskStatus(ts);
    eventService.save(eventToUpdate);
    return "redirect:/event/details?id=" + wrapper.getEventId();
  }


  @GetMapping(value = "event/deleteTaskFromEvent")
  public String deleteTaskFromEvent(final Model model, @RequestParam final Long taskId,
      @RequestParam final Long eventId) {
    final Event event = eventService.findById(eventId);
    event.removeTaskStatus(taskStatusService.findById(taskId));
    eventService.save(event);
    return "redirect:/event/details?id=" + eventId;
  }

}
