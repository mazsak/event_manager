package com.example.event_manager.controller;

import com.example.event_manager.model.Event;
import com.example.event_manager.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EventController {

  @Autowired private EventService eventService;

  @GetMapping("/")
  public String all(Model model) {
    List<Event> list = eventService.findAll();
    model.addAttribute("events", eventService.findAll());
    return "index";
  }

  @GetMapping("/event_add")
  public String add(Model model){

  }
}
