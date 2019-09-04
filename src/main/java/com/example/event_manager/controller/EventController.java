package com.example.event_manager.controller;

import com.example.event_manager.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {

  @Autowired private EventService eventService;

  @GetMapping("/")
  public String alla(Model model) {
    model.addAttribute("events", eventService.findAll());
    return "index";
  }
}
