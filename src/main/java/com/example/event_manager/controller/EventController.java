package com.example.event_manager.controller;

import com.example.event_manager.model.Event;
import com.example.event_manager.model.ToDo;
import com.example.event_manager.model.ToDoPredefined;
import com.example.event_manager.service.EventService;
import com.example.event_manager.service.ToDoPredefinedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EventController {

  @Autowired private EventService eventService;
  @Autowired private ToDoPredefinedService toDoPredefinedService;

  @GetMapping("/")
  public String all(Model model) {
    List<Event> list = eventService.findAll();
    model.addAttribute("events", eventService.findAll());
    return "index";
  }

  @GetMapping("/event_add")
  public String add(Model model) {
    model.addAttribute("event", new Event());
    model.addAttribute("predefineds", toDoPredefinedService.findAll());
    return "event_add";
  }

  @PostMapping(value = "/event_add", params = "action=save")
  public String save(Model model, @ModelAttribute Event event) {
    for (ToDoPredefined toDoPredefined : event.getToDoPredefineds()){
      ToDo toDo = ToDo.builder().name(toDoPredefined.getName()).build();
      toDo.setTaskToTaskStatus(toDoPredefined.getTasks());
      event.getToDos().add(toDo);
    }
    eventService.save(event);
    return "redirect:/";
  }
}
