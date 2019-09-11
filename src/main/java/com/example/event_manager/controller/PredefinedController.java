package com.example.event_manager.controller;

import com.example.event_manager.form.ToDoPredefinedForm;
import com.example.event_manager.service.ToDoPredefinedService;
import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("predefineds")
@AllArgsConstructor
public class PredefinedController {

  private final ToDoPredefinedService toDoPredefinedService;

  @GetMapping("/all")
  public String all(final Model model) {
    model.addAttribute("predefineds", toDoPredefinedService.findAllAndSortByName());
    return "predefined/list";
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable final String id) {
    toDoPredefinedService.delete(Long.valueOf(id));
    return "redirect:/predefineds/all";
  }

  @GetMapping("/add")
  public String add(final Model model) {
    model.addAttribute("predefined", ToDoPredefinedForm.builder().task("").build());
    return "/predefined/add";
  }

  @GetMapping("/add/{id}")
  public String edit(final Model model, @PathVariable final Long id) {
    model.addAttribute("predefined", toDoPredefinedService.findById(id));
    return "/predefined/add";
  }

  @PostMapping(value = "/add", params = "action=add")
  public String addTask(final Model model, @ModelAttribute final ToDoPredefinedForm predefined) {
    if (predefined.getTasks() == null) {
      predefined.setTasks(new ArrayList<>());
    }
    predefined.getTasks().add("");
    model.addAttribute("predefined", predefined);
    return "/predefined/add";
  }

  @PostMapping(value = "/add", params = "remove")
  public String deleteTask(
      final Model model,
      @RequestParam("remove") final String description,
      @ModelAttribute final ToDoPredefinedForm predefined) {
    predefined.getTasks().remove(description);
    model.addAttribute("predefined", predefined);
    return "/predefined/add";
  }

  @PostMapping(value = "/add", params = "action=save")
  public String save(@ModelAttribute final ToDoPredefinedForm predefined) {
    predefined.setTasks(
        predefined.getTasks().stream().filter(x -> !x.equals("")).collect(Collectors.toList()));
    toDoPredefinedService.save(predefined);
    return "redirect:/predefineds/all";
  }
}
