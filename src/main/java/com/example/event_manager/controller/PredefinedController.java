package com.example.event_manager.controller;

import com.example.event_manager.service.ToDoPredefinedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PredefinedController {

  @Autowired private ToDoPredefinedService toDoPredefinedServie;

  @GetMapping("/predefined")
  public String alla(Model model) {
    model.addAttribute("predefineds", toDoPredefinedServie.findAll());
    return "predefined_list";
  }
}
