package com.example.event_manager.controller;

import com.example.event_manager.enums.GroupName;
import com.example.event_manager.enums.SortHowEnum;
import com.example.event_manager.form.AllEventsForm;
import com.example.event_manager.form.BillingForm;
import com.example.event_manager.form.EventAddEditForm;
import com.example.event_manager.form.EventDetailsForm;
import com.example.event_manager.form.EventForm;
import com.example.event_manager.form.TaskStatusForm;
import com.example.event_manager.form.ToDoPredefinedForm;
import com.example.event_manager.form.ToDoPredefinedSimpleForm;
import com.example.event_manager.service.BillingReportService;
import com.example.event_manager.service.BillingService;
import com.example.event_manager.service.EventService;
import com.example.event_manager.service.PersonService;
import com.example.event_manager.service.TaskStatusService;
import com.example.event_manager.service.ToDoPredefinedService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import com.example.event_manager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("events")
@Controller
@AllArgsConstructor
public class EventController {

  private final PersonService personService;
  private final EventService eventService;
  private final UserService userService;
  private final TaskStatusService taskStatusService;
    private final BillingReportService billingReportService;
  private final ToDoPredefinedService toDoPredefinedService;
  private final BillingService billingService;


  @GetMapping("/delete/{id}")
  public String eventDelete(@PathVariable final Long id) {
    eventService.delete(id);

    return "redirect:/events/all";
  }

  @GetMapping("/started/{id}")
  public String eventChangeStarted(@PathVariable final Long id) {
    eventService.changeStarted(id);

    return "redirect:/events/details/" + id;
  }

  @GetMapping("/details/{id}")
  public String eventDetails(final Model model, @PathVariable final Long id) {
    final EventForm event = eventService.findById(id);
    event.separationTasksOnList();
    event.eventOutDatedCheck();

    model.addAttribute(
        "eventDetails",
        EventDetailsForm.builder()
            .event(event)
            .build());
    model.addAttribute("user", userService.getPrincipalSimple());

    return "/event/details";
  }

  @GetMapping("/details/{id}/status")
  public String eventDetailsChangeTaskStatus(
      final Model model,
      @PathVariable final Long id,
      @RequestParam(value = "index", required = false) final String index,
      @RequestParam(value = "element", required = false) final String element) {
    if (element.equals("billing")) {
      billingService.changeState(Long.valueOf(index));
    } else {
      taskStatusService.changeState(Long.valueOf(index));
    }

    return "redirect:/events/details/" + id;
  }

  @GetMapping("/add")
  public String add(final Model model) {
    model.addAttribute(
        "eventAddEditForm",
        EventAddEditForm.builder()
            .event(
                EventForm.builder()
                    .started(false)
                    .taskStatus(
                        TaskStatusForm.builder().status(false).taskStatusType("adhoc").build())
                    .billing(BillingForm.builder().build())
                    .build())
            .predefinedNameList(toDoPredefinedService.findAllSimple())
            .people(personService.findAll())
            .position(0)
            .collapses(Arrays.asList(true, false, false, false, false, false))
            .build());
    model.addAttribute("user", userService.getPrincipalSimple());

    return "event/add";
  }

  @GetMapping("/add/{id}")
  public String edit(final Model model, @PathVariable final Long id) {
    final EventForm event = eventService.findById(id);
    event.separationTasksOnList();
    if (event.getPredefinedList() == null) {
      event.setPredefinedList(new ArrayList<>());
    }

    final List<ToDoPredefinedSimpleForm> predefinedSimpleForms;
    if (event.getPredefinedList().isEmpty()) {
      predefinedSimpleForms = toDoPredefinedService.findAllSimple();
    } else {
      predefinedSimpleForms =
          toDoPredefinedService.findAllByNameNotInSimple(
              event.getPredefinedList().stream()
                  .map(e -> e.get(0).getTaskStatusType())
                  .collect(Collectors.toList()));
    }

    model.addAttribute(
        "eventAddEditForm",
        EventAddEditForm.builder()
            .event(event)
            .predefinedNameList(predefinedSimpleForms)
            .people(personService.findAll())
            .position(0)
            .collapses(Arrays.asList(true, false, false, false, false, false))
            .build());
    model.addAttribute("user", userService.getPrincipalSimple());

    return "/event/add";
  }

  @PostMapping(value = "/add", params = "action=addAdhoc")
  public String addAdhoc(final Model model, @ModelAttribute EventAddEditForm eventAddEditForm) {
    if (eventAddEditForm.getEvent().getTaskStatuses() == null) {
      eventAddEditForm.getEvent().setTaskStatuses(new ArrayList<>());
    }
    eventAddEditForm
        .getEvent()
        .getTaskStatuses()
        .add(TaskStatusForm.builder().status(false).taskStatusType("adhoc").build());
    eventAddEditForm = updateData(eventAddEditForm);

    model.addAttribute("eventAddEditForm", eventAddEditForm);
    model.addAttribute("user", userService.getPrincipalSimple());

    return "event/add";
  }

  @PostMapping(value = "/add", params = "removeAdhoc")
  public String removeAdhoc(
      final Model model,
      @ModelAttribute EventAddEditForm eventAddEditForm,
      @RequestParam("removeAdhoc") final String indexAdhoc) {
    final TaskStatusForm taskStatusRemove =
        eventAddEditForm.getEvent().getTaskStatuses().get(Integer.parseInt(indexAdhoc));
    eventAddEditForm.getEvent().getTaskStatuses().remove(taskStatusRemove);
    eventAddEditForm = updateData(eventAddEditForm);

    model.addAttribute("eventAddEditForm", eventAddEditForm);
    model.addAttribute("user", userService.getPrincipalSimple());

    return "event/add";
  }

  @PostMapping(value = "/add", params = "action=addBilling")
  public String addBilling(final Model model, @ModelAttribute EventAddEditForm eventAddEditForm) {
    if (eventAddEditForm.getEvent().getBillings() == null) {
      eventAddEditForm.getEvent().setBillings(new ArrayList<>());
    }
    eventAddEditForm.getEvent().getBillings().add(BillingForm.builder().build());
    eventAddEditForm = updateData(eventAddEditForm);

    model.addAttribute("eventAddEditForm", eventAddEditForm);
    model.addAttribute("user", userService.getPrincipalSimple());

    return "event/add";
  }

  @PostMapping(value = "/add", params = "removeBilling")
  public String removeBilling(
      final Model model,
      @ModelAttribute EventAddEditForm eventAddEditForm,
      @RequestParam("removeBilling") final String indexBilling) {
    final BillingForm billingRemove =
        eventAddEditForm.getEvent().getBillings().get(Integer.parseInt(indexBilling));
    eventAddEditForm.getEvent().getBillings().remove(billingRemove);
    eventAddEditForm = updateData(eventAddEditForm);

    model.addAttribute("eventAddEditForm", eventAddEditForm);
    model.addAttribute("user", userService.getPrincipalSimple());

    return "event/add";
  }

  @PostMapping(value = "/add", params = "addPredefined")
  public String addPredefined(
      final Model model,
      @RequestParam("addPredefined") final ToDoPredefinedSimpleForm predefined,
      @ModelAttribute EventAddEditForm eventAddEditForm) {
    final ToDoPredefinedForm predefinedForm = toDoPredefinedService.findById(predefined.getId());
    if (!predefinedForm.getTasks().isEmpty()) {
      eventAddEditForm.getEvent().addToDoPredefined(predefinedForm);
    }
    eventAddEditForm = updateData(eventAddEditForm);

    model.addAttribute("eventAddEditForm", eventAddEditForm);
    model.addAttribute("user", userService.getPrincipalSimple());

    return "event/add";
  }

  @PostMapping(value = "/add", params = "removePredefined")
  public String removePredefined(
      final Model model,
      @RequestParam("removePredefined") final String indexPredefined,
      @ModelAttribute EventAddEditForm eventAddEditForm) {
    final List<TaskStatusForm> predefinedRemove =
        eventAddEditForm.getEvent().getPredefinedList().get(Integer.parseInt(indexPredefined));
    eventAddEditForm.getEvent().getPredefinedList().remove(predefinedRemove);
    eventAddEditForm = updateData(eventAddEditForm);

    model.addAttribute("eventAddEditForm", eventAddEditForm);
    model.addAttribute("user", userService.getPrincipalSimple());

    return "event/add";
  }

  @PostMapping(value = "add", params = "action=save")
  public String save(
      @ModelAttribute("eventAddEditForm") @Valid EventAddEditForm eventAddEditForm,
      final BindingResult bindingResult,
      final Model model) {
    if (bindingResult.hasErrors()) {
      eventAddEditForm = updateData(eventAddEditForm);

      model.addAttribute("eventAddEditForm", eventAddEditForm);
      model.addAttribute("user", userService.getPrincipalSimple());

      return "event/add";
    } else {
      eventAddEditForm.getEvent().makeToOneList();
      eventService.save(eventAddEditForm.getEvent());
      return "redirect:/events/all";
    }
  }

  @GetMapping(value = "all")
  public String all(final Model model) {
    final Pageable pagingS = PageRequest.of(0, 6);
    final Pageable pagingN = PageRequest.of(0, 6);
    final Pageable pagingO = PageRequest.of(0, 6);
    model.addAttribute("events", eventService.getPartition(pagingS, pagingN, pagingO));
    model.addAttribute("user", userService.getPrincipalSimple());

    return "event/list";
  }

  @GetMapping(value = "all", params = "search")
  public String search(final Model model, @ModelAttribute final AllEventsForm allEventsForm) {
    final Pageable pagingS = PageRequest.of(0, 6);
    final Pageable pagingN = PageRequest.of(0, 6);
    final Pageable pagingO = PageRequest.of(0, 6);
    model.addAttribute(
            "events",
        eventService.searchByNamePlaceTopic(allEventsForm.getQuery(), pagingS, pagingN, pagingO));
    model.addAttribute("user", userService.getPrincipalSimple());
    return "event/list";
  }

  @GetMapping(value = "all", params = "previous")
  public String previous(
          final Model model,
          @RequestParam final String sortHowEnum,
          @RequestParam final String groupName,
          @RequestParam final String pageNumberOfCurrentGroup,
          @RequestParam final String pageSizeOfCurrentGroup,
          @RequestParam final String pageNumberOfSecondGroup,
          @RequestParam final String pageSizeOfSecondGroup,
          @RequestParam final String pageNumberOfThirdGroup,
          @RequestParam final String pageSizeOfThirdGroup,
          @RequestParam final String query) {
    model.addAttribute(
            "events",
            blocOfCode(
                    sortHowEnum,
                    GroupName.valueOf(groupName),
                    Integer.parseInt(pageNumberOfCurrentGroup) - 1,
                    Integer.parseInt(pageSizeOfCurrentGroup),
                    Integer.parseInt(pageNumberOfSecondGroup),
                    Integer.parseInt(pageSizeOfSecondGroup),
                    Integer.parseInt(pageNumberOfThirdGroup),
                    Integer.parseInt(pageSizeOfThirdGroup),
                    query,
                    null));
    model.addAttribute("user", userService.getPrincipalSimple());
    return "event/list";
  }

  @GetMapping(value = "all", params = "next")
  public String next(
          final Model model,
          @RequestParam final String sortHowEnum,
          @RequestParam final String groupName,
          @RequestParam final String pageNumberOfCurrentGroup,
          @RequestParam final String pageSizeOfCurrentGroup,
          @RequestParam final String pageNumberOfSecondGroup,
          @RequestParam final String pageSizeOfSecondGroup,
          @RequestParam final String pageNumberOfThirdGroup,
          @RequestParam final String pageSizeOfThirdGroup,
          @RequestParam final String query) {
    model.addAttribute(
            "events",
            blocOfCode(
                    sortHowEnum,
                    GroupName.valueOf(groupName),
                    Integer.parseInt(pageNumberOfCurrentGroup) + 1,
                    Integer.parseInt(pageSizeOfCurrentGroup),
                    Integer.parseInt(pageNumberOfSecondGroup),
                    Integer.parseInt(pageSizeOfSecondGroup),
                    Integer.parseInt(pageNumberOfThirdGroup),
                    Integer.parseInt(pageSizeOfThirdGroup),
                    query,
                    null));
    model.addAttribute("user", userService.getPrincipalSimple());
    return "event/list";
  }

  @GetMapping(value = "all", params = "changePage")
  public String changePage(
          final Model model,
          @RequestParam final String sortHowEnum,
          @RequestParam final String groupName,
          @RequestParam final String pageNumberOfCurrentGroup,
          @RequestParam final String pageSizeOfCurrentGroup,
          @RequestParam final String pageNumberOfSecondGroup,
          @RequestParam final String pageSizeOfSecondGroup,
          @RequestParam final String pageNumberOfThirdGroup,
          @RequestParam final String pageSizeOfThirdGroup,
          @RequestParam final String query,
          @RequestParam final String changePage) {
    model.addAttribute(
            "events",
            blocOfCode(
                    sortHowEnum,
                    GroupName.valueOf(groupName),
                    Integer.parseInt(changePage),
                    Integer.parseInt(pageSizeOfCurrentGroup),
                    Integer.parseInt(pageNumberOfSecondGroup),
                    Integer.parseInt(pageSizeOfSecondGroup),
                    Integer.parseInt(pageNumberOfThirdGroup),
                    Integer.parseInt(pageSizeOfThirdGroup),
                    query,
                    null));
    model.addAttribute("user", userService.getPrincipalSimple());
    return "event/list";
  }

  @GetMapping(value = "all", params = "sort")
  public String sort(
          final Model model,
          @RequestParam final String sortHowEnum,
          @RequestParam final String groupName,
          @RequestParam final String pageNumberOfCurrentGroup,
          @RequestParam final String pageSizeOfCurrentGroup,
          @RequestParam final String pageNumberOfSecondGroup,
          @RequestParam final String pageSizeOfSecondGroup,
          @RequestParam final String pageNumberOfThirdGroup,
          @RequestParam final String pageSizeOfThirdGroup,
          @RequestParam final String query,
          @RequestParam final String sort) {

    model.addAttribute(
            "events",
            blocOfCode(
                    sortHowEnum,
                    GroupName.valueOf(groupName),
                    Integer.parseInt(pageNumberOfCurrentGroup),
                    Integer.parseInt(pageSizeOfCurrentGroup),
                    Integer.parseInt(pageNumberOfSecondGroup),
                    Integer.parseInt(pageSizeOfSecondGroup),
                    Integer.parseInt(pageNumberOfThirdGroup),
                    Integer.parseInt(pageSizeOfThirdGroup),
                    query,
                    sort));
    model.addAttribute("user", userService.getPrincipalSimple());
    return "event/list";
  }

  private EventAddEditForm updateData(final EventAddEditForm eventAddEditForm) {
    eventAddEditForm.setPeople(personService.findAll());
    if (CollectionUtils.isEmpty(eventAddEditForm.getEvent().getPredefinedList())) {
      eventAddEditForm.setPredefinedNameList(toDoPredefinedService.findAllSimple());
    } else {
      eventAddEditForm.setPredefinedNameList(
          toDoPredefinedService.findAllByNameNotInSimple(
              eventAddEditForm.getEvent().getPredefinedList().stream()
                  .map(x -> x.get(0).getTaskStatusType())
                  .collect(Collectors.toList())));
    }

    return eventAddEditForm;
  }

  private AllEventsForm blocOfCode(
          final String sortHowEnum,
          final GroupName groupName,
          final int pageNumberOfCurrentGroup,
          final int pageSizeOfCurrentGroup,
          final int pageNumberOfSecondGroup,
          final int pageSizeOfSecondGroup,
          final int pageNumberOfThirdGroup,
          final int pageSizeOfThirdGroup,
          final String query,
          final String sort) {
    SortHowEnum sortHow = SortHowEnum.valueOf(sortHowEnum);
    if (sort != null) {
      if (sortHowEnum.contains(sort)) {
        if (sortHowEnum.contains("_ASC")) {
          sortHow = SortHowEnum.valueOf(sort + "_DSC");
        } else {
          sortHow = SortHowEnum.valueOf(sort + "_ASC");
        }
      } else {
        sortHow = SortHowEnum.valueOf(sort + "_ASC");
      }
    }
    final Pageable pagingS;
    final Pageable pagingN;
    final Pageable pagingO;
    switch (groupName) {
      case NOTSTARTED:
        pagingN = PageRequest.of(pageNumberOfCurrentGroup, pageSizeOfCurrentGroup, sortHow.sort);
        pagingS = PageRequest.of(pageNumberOfSecondGroup, pageSizeOfSecondGroup);
        pagingO = PageRequest.of(pageNumberOfThirdGroup, pageSizeOfThirdGroup);
        break;
      case OUTDATED:
        pagingO = PageRequest.of(pageNumberOfCurrentGroup, pageSizeOfCurrentGroup, sortHow.sort);
        pagingS = PageRequest.of(pageNumberOfSecondGroup, pageSizeOfSecondGroup);
        pagingN = PageRequest.of(pageNumberOfThirdGroup, pageSizeOfThirdGroup);
        break;
      case STARTED:
      default:
        pagingS = PageRequest.of(pageNumberOfCurrentGroup, pageSizeOfCurrentGroup, sortHow.sort);
        pagingN = PageRequest.of(pageNumberOfSecondGroup, pageSizeOfSecondGroup);
        pagingO = PageRequest.of(pageNumberOfThirdGroup, pageSizeOfThirdGroup);
        break;
    }

    final AllEventsForm allEventsForm;
    if (query == null || query.isEmpty()) {
      allEventsForm = eventService.getPartition(pagingS, pagingN, pagingO);
    } else {
      allEventsForm = eventService.searchByNamePlaceTopic(query, pagingS, pagingN, pagingO);
    }
    switch (groupName) {
      case STARTED:
        allEventsForm.getStarted().setSortHowEnum(sortHow);
        break;
      case OUTDATED:
        allEventsForm.getOutdated().setSortHowEnum(sortHow);
        break;
      case NOTSTARTED:
        allEventsForm.getNotStarted().setSortHowEnum(sortHow);
        break;
    }
    return allEventsForm;
  }
}