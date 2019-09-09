package com.example.event_manager.model;

import com.example.event_manager.form.TaskStatusForm;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "task_status")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatus {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime date;

  private boolean status;
  private String taskStatusType;

  @ManyToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY) //!!!
  private Event event;
  public TaskStatusForm mapToTaskStatusForm() {
    return TaskStatusForm.builder()
        .id(id)
        .name(name)
        .date(date)
        .status(status)
        .taskStatusType(taskStatusType)
        .event(event.mapToEventForm())
        .build();
  }
  
  @ManyToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY) //!!!
  private Person person;
}
