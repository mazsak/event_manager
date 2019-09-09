package com.example.event_manager.controller;

import com.example.event_manager.EventForm;
import com.example.event_manager.model.Event;
import com.example.event_manager.model.TaskStatus;
import com.example.event_manager.model.ToDo;
import com.example.event_manager.model.ToDoPredefined;
import com.example.event_manager.service.EventService;
import com.example.event_manager.service.TaskStatusService;
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
import java.util.stream.Collectors;

@Controller
public class EventController {

  @Autowired private EventService eventService;
  @Autowired private ToDoPredefinedService toDoPredefinedService;
  @Autowired private ToDoService toDoService;
  @Autowired private TaskStatusService taskStatusService;

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

  @GetMapping("/task_change")
  public String change(@RequestParam String id, @RequestParam String idEvent) {
    TaskStatus taskStatus = taskStatusService.findById(Long.parseLong(id));
    taskStatus.setStatus(!taskStatus.isStatus());
    taskStatusService.save(taskStatus);
    return "redirect:/event?id=" + idEvent;
  }

  @GetMapping("/event_edit")
  public String edit(Model model, @RequestParam String id) {
    Event event = eventService.findById(Long.valueOf(id));
    List<ToDoPredefined> predefineds = toDoPredefinedService.findAll();

    List<ToDoPredefined> belong = new ArrayList<>();
    for (ToDo toDo : event.getToDos()) {
      belong.addAll(
          predefineds.stream()
              .filter(x -> x.getName().equals(toDo.getName()))
              .collect(Collectors.toList()));
    }

    EventForm eventForm =
        EventForm.builder()
            .id(event.getId())
            .name(event.getName())
            .description(event.getDescription())
            .place(event.getPlace())
            .topic(event.getTopic())
            .dateTime(event.getDateTime())
            .toDoPredefineds(belong)
            .adhoc(event.getToDos().get(event.getToDos().size() - 1))
            .build();

    model.addAttribute("event", eventForm);
    model.addAttribute("predefineds", predefineds);
    return "event_edit";
  }

  @PostMapping(value = "/event_edit", params = "action=add")
  public String editAddElement(Model model, @ModelAttribute EventForm event) {
    TaskStatus taskStatus = TaskStatus.builder().name("").status(false).build();
    event.getAdhoc().getTasks().add(taskStatus);
    model.addAttribute("event", event);
    model.addAttribute("predefineds", toDoPredefinedService.findAll());
    return "event_edit";
  }

  @PostMapping(value = "/event_edit", params = "action=save")
  public String editSave(Model model, @ModelAttribute EventForm event) {
    Event save = eventService.findById(event.getId());

    if (!save.getName().equals(event.getName())) {
      save.setName(event.getName());
    }

    if (!save.getDescription().equals(event.getDescription())) {
      save.setDescription(event.getDescription());
    }

    if (!save.getPlace().equals(event.getPlace())) {
      save.setPlace(event.getPlace());
    }

    if (!save.getTopic().equals(event.getTopic())) {
      save.setTopic(event.getTopic());
    }

    if (!save.getDateTime().isEqual(event.getDateTime())) {
      save.setDateTime(event.getDateTime());
    }

    if (!event.getToDoPredefineds().isEmpty()) {
      for (ToDo toDo : save.getToDos()) {
        List<ToDoPredefined> predefineds =
            event.getToDoPredefineds().stream()
                .filter(x -> x.getName().equals(toDo.getName()))
                .collect(Collectors.toList());
        if (!predefineds.isEmpty()) {
          event.getToDoPredefineds().remove(predefineds.get(0));
        } else if (toDo.isPredefined()) {
          save.getToDos().remove(toDo);
        }
      }
    }else{
      for (ToDo toDo : save.getToDos()) {
        if(!toDo.getName().equals("TODO")){
          save.getToDos().remove(toDo);
        }
      }
    }

    List<ToDo> toDos = new ArrayList<>();
    if (!event.getToDoPredefineds().isEmpty()) {
      for (ToDoPredefined toDoPredefined : event.getToDoPredefineds()) {
        ToDo toDo = ToDo.builder().name(toDoPredefined.getName()).predefined(true).build();
        toDo.setTaskToTaskStatus(toDoPredefined.getTasks());
        toDos.add(toDo);
      }
      save.getToDos().addAll(0, toDoService.saveAllAndGet(toDos));
    }

    for (TaskStatus taskStatus : save.getToDos().get(save.getToDos().size() - 1).getTasks()) {
      if (taskStatus.getName().isEmpty()
          && save.getToDos().get(save.getToDos().size() - 1).getTasks().size() > 1) {
        save.getToDos().get(save.getToDos().size() - 1).getTasks().remove(taskStatus);
      }
    }

    eventService.save(save);
    return "redirect:/event?id=" + save.getId();
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
  public String addElement(Model model, @ModelAttribute EventForm event) {
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
      if (taskStatus.getName().isEmpty() && event.getAdhoc().getTasks().size() > 1) {
        event.getAdhoc().getTasks().remove(taskStatus);
      }
    }
    event.getAdhoc().setName("TODO");
    toDos.add(event.getAdhoc());

    save.setToDos(toDoService.saveAllAndGet(toDos));
    eventService.save(save);
    return "redirect:/";
  }
}
