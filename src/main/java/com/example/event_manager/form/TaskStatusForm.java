package com.example.event_manager.form;

import com.example.event_manager.model.TaskStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatusForm {
  private Long id;
  private String name;
  private boolean status;
  private String taskStatusType;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime date;

  private EventForm event;

  public TaskStatus mapToTaskStatus() {
    return TaskStatus.builder()
        .id(id)
        .name(name)
        .status(status)
        .taskStatusType(taskStatusType)
        .date(date)
        .event(event.mapToEvent())
        .build();
  }
}
