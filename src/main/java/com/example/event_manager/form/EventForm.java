package com.example.event_manager.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EventForm {

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
}
