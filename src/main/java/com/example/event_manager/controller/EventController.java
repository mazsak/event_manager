package com.example.event_manager.controller;

import com.example.event_manager.service.IEventServie;
import com.example.event_manager.service.IToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {

  @Autowired private IToDoService toDoService;
  @Autowired private IEventServie eventServie;

  @GetMapping("/")
  public String all(Model model) {
    model.addAttribute("todos", toDoService.findById((long) 1));
    return "showToDo";
  }

  @GetMapping("/item")
  public String all() {
    return "event";
  }

  @GetMapping("/index")
  public String alla(Model model) {
    model.addAttribute("events", eventServie.findAll());
    return "index";
  }
}
