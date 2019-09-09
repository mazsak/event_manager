package com.example.event_manager.form;

import com.example.event_manager.model.Event;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

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

  public Event mapToEvent() {
    return Event.builder()
        .id(id)
        .name(name)
        .description(description)
        .topic(topic)
        .place(place)
        .dateTime(dateTime)
        .taskStatuses(taskStatuses.stream().map(x -> x.mapToTaskStatus()).collect(Collectors.toList()))
        .build();
  }
}
