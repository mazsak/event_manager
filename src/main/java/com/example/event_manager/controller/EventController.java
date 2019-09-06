package com.example.event_manager.controller;

import com.example.event_manager.EventForm;
import com.example.event_manager.model.Event;
import com.example.event_manager.model.TaskStatus;
import com.example.event_manager.model.ToDo;
import com.example.event_manager.model.ToDoPredefined;
import com.example.event_manager.service.EventService;
import com.example.event_manager.service.ToDoPredefinedService;
import com.example.event_manager.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EventController {

  @Autowired private EventService eventService;
  @Autowired private ToDoPredefinedService toDoPredefinedService;
  @Autowired private ToDoService toDoService;

  @GetMapping("/")
  public String all(Model model) {
    List<Event> list = eventService.findAll();
    model.addAttribute("events", eventService.findAll());
    return "index";
  }

  @GetMapping("/event")
  public String one(Model model, @RequestParam String id) {
    Event event = eventService.findById(Long.parseLong(id));
    model.addAttribute("event", event);
    LocalDateTime localDateTime = LocalDateTime.now();
    localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    boolean isDone = event.getDateTime().isAfter(localDateTime);
    model.addAttribute("isDone", isDone);
    return "event";
  }

  @GetMapping("/event_delete")
  public String delete(Model model, @RequestParam String id) {
    eventService.delete(Long.parseLong(id));
    return "redirect:/";
  }

  @GetMapping("/event_add")
  public String edit(Model model, @RequestParam String id) {
    Event event = eventService.findById(Long.valueOf(id));
    EventForm eventForm =
        EventForm.builder()
            .name(event.getName())
            .description(event.getDescription())
            .place(event.getPlace())
            .topic(event.getTopic())
            .dateTime(event.getDateTime())
            .build();

    for(ToDo toDo : event.getToDos()){
      if (toDo.isPredefined()){
        
      }else{
        eventForm.setAdhoc(toDo);
      }
    }

    model.addAttribute("predefineds", toDoPredefinedService.findAll());
    return "event_edit";
  }

  @PostMapping(value = "/event_add", params = "action=add")
  public String edit_add_element(Model model, @ModelAttribute EventForm event) {
    TaskStatus taskStatus = TaskStatus.builder().name("").status(false).build();
    event.getAdhoc().getTasks().add(taskStatus);
    model.addAttribute("event", event);
    model.addAttribute("predefineds", toDoPredefinedService.findAll());
    return "event_add";
  }

  @GetMapping("/event_add")
  public String add(Model model) {
    EventForm event = new EventForm();
    event.setAdhoc(
        ToDo.builder()
            .name("TODO")
            .predefined(false)
            .task(TaskStatus.builder().name("").status(false).build())
            .build());
    model.addAttribute("event", event);
    model.addAttribute("predefineds", toDoPredefinedService.findAll());
    return "event_add";
  }

  @PostMapping(value = "/event_add", params = "action=add")
  public String add_element(Model model, @ModelAttribute EventForm event) {
    TaskStatus taskStatus = TaskStatus.builder().name("").status(false).build();
    event.getAdhoc().getTasks().add(taskStatus);
    model.addAttribute("event", event);
    model.addAttribute("predefineds", toDoPredefinedService.findAll());
    return "event_add";
  }

  @PostMapping(value = "/event_add", params = "action=save")
  public String save(Model model, @ModelAttribute EventForm event) {
    Event save =
        Event.builder()
            .name(event.getName())
            .topic(event.getTopic())
            .description(event.getDescription())
            .place(event.getPlace())
            .dateTime(event.getDateTime())
            .build();
    List<ToDo> toDos = new ArrayList<>();
    for (ToDoPredefined toDoPredefined : event.getToDoPredefineds()) {
      ToDo toDo = ToDo.builder().name(toDoPredefined.getName()).predefined(true).build();
      toDo.setTaskToTaskStatus(toDoPredefined.getTasks());
      toDos.add(toDo);
    }

    for (TaskStatus taskStatus : event.getAdhoc().getTasks()) {
      if (taskStatus.getName().isEmpty()) {
        event.getAdhoc().getTasks().remove(taskStatus);
      }
    }
    if (!event.getAdhoc().getTasks().isEmpty()) {
      event.getAdhoc().setName("TODO");
      toDos.add(event.getAdhoc());
    }
    save.setToDos(toDoService.saveAllAndGet(toDos));
    eventService.save(save);
    return "redirect:/";
  }
}
