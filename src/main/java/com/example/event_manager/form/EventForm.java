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
  private String name;
  private String description;
  private String topic;
  private String place;
  private Boolean started;

  @Singular
  private List<List<TaskStatusForm>> predefineds;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime dateTime = LocalDateTime.now();

  @Singular
  private List<TaskStatusForm> taskStatuses;

  @Singular
  private List<BillingForm> billings = new ArrayList<>();

  public void separationTasksOnList() {
    final Set<String> namePredefined =
        taskStatuses.stream().map(TaskStatusForm::getTaskStatusType).collect(Collectors.toSet());
    namePredefined.remove("adhoc");
    predefineds = new ArrayList<>();

    for (final String name : namePredefined) {
      predefineds.add(
          taskStatuses.stream()
              .filter(x -> x.getTaskStatusType().equals(name))
              .collect(Collectors.toList()));
    }

    setTaskStatuses(
        taskStatuses.stream()
            .filter(x -> x.getTaskStatusType().equals("adhoc"))
            .collect(Collectors.toList()));
  }

  public void makeToOneList() {
    if (taskStatuses == null) {
      taskStatuses = new ArrayList<>();
    }
    if (predefineds != null && !predefineds.isEmpty()) {
      predefineds.forEach(taskStatuses::addAll);
      predefineds.clear();
    }
  }

  public void addToDoPredefined(final ToDoPredefinedForm predefined) {
    if (predefineds == null) {
      predefineds = new ArrayList<>();
    }
    predefineds.add(
        predefined.getTasks().stream()
            .map(
                x ->
                    TaskStatusForm.builder()
                        .name(x)
                        .status(false)
                        .taskStatusType(predefined.getName())
                        .build())
            .collect(Collectors.toList()));
  }
}
