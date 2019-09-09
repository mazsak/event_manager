package com.example.event_manager.controller;

import com.example.event_manager.model.ToDoPredefined;
import com.example.event_manager.service.ToDoPredefinedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PredefinedController {

//  @Autowired private ToDoPredefinedService toDoPredefinedService;
//
//  @GetMapping("/predefined")
//  public String all(Model model) {
//    model.addAttribute("predefineds", toDoPredefinedService.findAll());
//    return "predefined_list";
//  }
//
//  @GetMapping("/predefined_add")
//  public String add(Model model) {
//    model.addAttribute("predefined", new ToDoPredefined());
//    return "predefined_add";
//  }
//
//  @GetMapping("/predefined_edit")
//  public String edit(Model model, @RequestParam String id) {
//    model.addAttribute("predefined", toDoPredefinedService.findById(Long.valueOf(id)));
//    return "predefined_add";
//  }
//
//  @GetMapping("/predefined_delete")
//  public String delete(Model model, @RequestParam String id) {
//    toDoPredefinedService.delete(Long.valueOf(id));
//    return "redirect:/predefined";
//  }
//
//
//  @PostMapping(value = "/predefined_add", params = "action=add")
//  public String addElement(Model model, @ModelAttribute ToDoPredefined predefined) {
//    predefined.getTasks().add(Task.builder().name("").build());
//    model.addAttribute("predefined", predefined);
//    return "predefined_add";
//  }
//
//  @PostMapping(value = "/predefined_add", params = "action=save")
//  public String save(Model model, @ModelAttribute ToDoPredefined predefined) {
//    for(Task task : predefined.getTasks()){
//      if (task.getName().isEmpty()){
//        predefined.getTasks().remove(task);
//      }
//    }
//    toDoPredefinedService.save(predefined);
//    model.addAttribute("predefineds", toDoPredefinedService.findAll());
//    return "redirect:/predefined";
//  }
}
