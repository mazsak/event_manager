package com.example.event_manager.model;

import com.example.event_manager.form.EventForm;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private String topic;
  private String place;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime dateTime = LocalDateTime.now();

  @OneToMany @Singular private Set<TaskStatus> taskStatuses;

  public EventForm mapToEventForm() {
    return EventForm.builder()
        .id(id)
        .name(name)
        .description(description)
        .topic(topic)
        .place(place)
        .dateTime(dateTime)
        .taskStatuses(taskStatuses.stream().map(x -> x.mapToTaskStatusForm()).collect(Collectors.toList()))
        .build();
  }
}
