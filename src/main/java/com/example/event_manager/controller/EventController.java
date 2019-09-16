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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping("/events/")
public class EventController {

  private final PersonService personService;
  private final EventService eventService;
  private final TaskStatusService taskStatusService;

  @GetMapping("details")
  public String detailsEvent(final Model model, @RequestParam final Long id) {
    final Event event = this.eventService.findById(id);
    final Map<String, List<TaskStatus>> map = this.eventService.preapreTasksForEvent(event);
    model.addAttribute("tasks", map);
    model.addAttribute("event", event);
    return "/event/details";
  }

  @GetMapping("addAdhocTaskToEventForm")
  public String addAdhocToEvent(final Model model, @RequestParam final Long id) {
    final TaskForEventCreationWrapper wrapper = new TaskForEventCreationWrapper(id);
    model.addAttribute("taskStatusForEventCreatingDto", wrapper);
    model.addAttribute("persons", this.personService.findAll());
    return "event/addAdhoc";
  }

  @PostMapping(value = "saveAdhocTaskToEvent")
  public String saveAdhocToEvent(final Model model,
      @ModelAttribute(value = "taskStatusForEventCreatingDto") final TaskForEventCreationWrapper wrapper) {
    final TaskStatus ts = new TaskStatus();
    final Person person = this.personService.findById(wrapper.getPersonId());
    ts.setTaskStatusType("adhoc");
    ts.setName(wrapper.getName());
    ts.setDate(wrapper.getDate());
    person.addTaskStatus(ts);
    final Event eventToUpdate = this.eventService.findById(wrapper.getEventId());
    eventToUpdate.addTaskStatus(ts);
    eventService.save(eventToUpdate);
    return "redirect:/event/details?id=" + wrapper.getEventId();
  }

  @GetMapping(value = "deleteTaskFromEvent")
  public String deleteTaskFromEvent(final Model model, @RequestParam final Long taskId,
      @RequestParam final Long eventId) {
    final Event event = this.eventService.findById(eventId);
    final TaskStatus toDelete = this.taskStatusService.findById(taskId);
    event.removeTaskStatus(toDelete);
    eventService.save(event);
    return "redirect:/event/details?id=" + eventId;
  }

  @GetMapping(value = "editTask")
  public String editTaskStatus(final Model model, @RequestParam final Long taskId) {
    model.addAttribute("task", this.taskStatusService.findById(taskId));
    model.addAttribute("persons", this.personService.findAll());
    return "event/editTaskStatus";
  }

  @PostMapping(value = "editTask/save")
  public String saveEditedTask(@ModelAttribute(value = "task") final TaskStatus taskStatus) {
    taskStatusService.update(taskStatus);
    return "redirect:/event/details?id=" + taskStatus.getEvent().getId();
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

}
