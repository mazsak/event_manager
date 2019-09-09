package com.example.event_manager.controller;

import com.example.event_manager.model.ToDoPredefined;
import com.example.event_manager.service.ToDoPredefinedService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("predefined")
@AllArgsConstructor
public class PredefinedController {

    private final ToDoPredefinedService toDoPredefinedService;

    @GetMapping("")
    public String all(final Model model) {
        model.addAttribute("predefineds", this.toDoPredefinedService.findAll());
        model.addAttribute("predefinedAdd", ToDoPredefined.builder().task("").build());

        return "predefined/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam final String id) {
        this.toDoPredefinedService.delete(Long.valueOf(id));
        return "redirect:/predefined";
    }
}
