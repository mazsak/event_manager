package com.example.event_manager.form;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventForm {

  @PersistenceUnit
  EntityManagerFactory entityManagerFactory;

  private Long id;

  @NotNull
  @Size(min = 2, max = 20, message = "Name length must be between 3 and 255")
  private String name;

  @NotNull
  @Size(min = 3, max = 255, message = "Description length must be between 3 and 255")
  private String description;

  @NotNull
  @Size(min = 3, max = 255, message = "Topic length must be between 3 and 255")
  private String topic;

  @NotNull
  @Size(min = 3, max = 255, message = "Place length must be between 3 and 255")
  private String place;

  private Boolean started;

  private Boolean outdated;

  @Singular(value = "predefinedList")
  private List<List<TaskStatusForm>> predefinedList;

  @NotNull(message = "Select date and time")
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime dateTime;

  @Singular
  @Valid
  private List<TaskStatusForm> taskStatuses;

  @Singular
  @Valid
  private List<BillingForm> billings = new ArrayList<>();

  public void separationTasksOnList() {
    final Set<String> namePredefined =
        taskStatuses.stream().map(TaskStatusForm::getTaskStatusType).collect(Collectors.toSet());
    namePredefined.remove("adhoc");
    predefinedList = new ArrayList<>();

    predefinedList.addAll(
        namePredefined.stream()
            .map(
                name ->
                    taskStatuses.stream()
                        .filter(task -> name.equals(task.getTaskStatusType()))
                        .collect(Collectors.toList()))
            .collect(Collectors.toList()));

    taskStatuses =
        taskStatuses.stream()
            .filter(task -> task.getTaskStatusType().equals("adhoc"))
            .collect(Collectors.toList());
  }

  public void makeToOneList() {
    if (taskStatuses == null) {
      taskStatuses = new ArrayList<>();
    }
    if (predefinedList != null && !predefinedList.isEmpty()) {
      predefinedList.forEach(taskStatuses::addAll);
      predefinedList.clear();
    }
  }

  public void addToDoPredefined(final ToDoPredefinedForm predefined) {
    if (predefinedList == null) {
      predefinedList = new ArrayList<>();
    }
    predefinedList.add(
        predefined.getTasks().stream()
            .map(
                name ->
                    TaskStatusForm.builder()
                        .name(name)
                        .status(false)
                        .taskStatusType(predefined.getName())
                        .build())
            .collect(Collectors.toList()));
  }

  public void eventOutDatedCheck() {
    outdated = dateTime.isAfter(LocalDateTime.now());
  }

  public void deleteBillingsById(List<Long> ids) {
    billings = billings
        .stream()
        .filter(billing -> !ids.contains(billing.getId()))
        .collect(Collectors.toList());
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventForm eventForm = (EventForm) o;
    return Objects.equals(id, eventForm.id) &&
        Objects.equals(name, eventForm.name) &&
        Objects.equals(description, eventForm.description) &&
        Objects.equals(topic, eventForm.topic) &&
        Objects.equals(place, eventForm.place) &&
        Objects.equals(started, eventForm.started) &&
        Objects.equals(outdated, eventForm.outdated) &&
        Objects.equals(predefinedList, eventForm.predefinedList) &&
        Objects.equals(dateTime, eventForm.dateTime) &&
        Objects.equals(taskStatuses, eventForm.taskStatuses) &&
        Objects.equals(billings, eventForm.billings);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(id, name, description, topic, place, started, outdated, predefinedList, dateTime,
            taskStatuses, billings);
  }
  public boolean returnFalseWhenAreTheSame(BillingForm bi) {
    if(bi==null || bi.getId() == null)
      return false;
    for (BillingForm billingForm : billings) {
      if (billingForm.getId().equals(bi.getId())
          && billingForm.isConfirmed() == bi.isConfirmed()
          && billingForm.getMoney().equals(bi.getMoney())
          && billingForm.getTitle().equals(bi.getTitle())
          && billingForm.getPersonAssigned().getId().equals(bi.getPersonAssigned().getId())
          && billingForm.getBillingType().equals(bi.getBillingType())
          && billingForm.getDateOfCreation().equals(bi.getDateOfCreation())
          && billingForm.getDateOfConfirm().equals(bi.getDateOfConfirm())) {
        return false;
      }
    }

    return true;
  }
}
