package com.example.event_manager.form;

import com.example.event_manager.model.Event;
import com.example.event_manager.model.TaskStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventForm {
  private Long id;
  private String name;
  private String description;
  private String topic;
  private String place;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime dateTime = LocalDateTime.now();

  @Singular private Set<TaskStatusForm> taskStatuses;

  public Event mapToEvent(){
    List<TaskStatus>
    Event event = Event.builder().id(id).name(name).description(description).topic(topic).place(place).dateTime(dateTime).taskStatuses(taskStatuses.stream().map(x -> x.mapToTaskStatus())).build();
    return
  }
}
