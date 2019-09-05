package com.example.event_manager.controller;

import com.example.event_manager.model.Task;
import com.example.event_manager.model.ToDoPredefined;
import com.example.event_manager.service.ToDoPredefinedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PredefinedController {

  @Autowired private ToDoPredefinedService toDoPredefinedService;

  @GetMapping("/predefined")
  public String all(Model model) {
    model.addAttribute("predefineds", toDoPredefinedService.findAll());
    return "predefined_list";
  }

  @GetMapping("/predefined_add")
  public String add(Model model) {
    model.addAttribute("predefined", new ToDoPredefined());
    return "predefined_add";
  }

  @PostMapping(value = "/predefined_add", params = "action=add")
  public String add_element(Model model, @ModelAttribute ToDoPredefined predefined) {
    predefined.getTasks().add(Task.builder().name("").build());
    model.addAttribute("predefined", predefined);
    return "predefined_add";
  }

  @PostMapping(value = "/predefined_add", params = "action=save")
  public String save(Model model, @ModelAttribute ToDoPredefined predefined) {
    for(int i = 0 ; i < predefined.getTasks().size(); i++){
      if (predefined.getTasks().get(i).getName().isEmpty()){
        predefined.getTasks().remove(i);
      }
    }
    toDoPredefinedService.save(predefined);
    model.addAttribute("predefineds", toDoPredefinedService.findAll());
    return "redirect:/predefined";
  }
}
