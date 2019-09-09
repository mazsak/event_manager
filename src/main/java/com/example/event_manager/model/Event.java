package com.example.event_manager.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
  
  @Singular
  @OneToMany(cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private Set<TaskStatus> taskStatuses;
  public void addTaskStatus(TaskStatus ts){
    taskStatuses.add(ts);
    ts.setEvent(this);
  }
  
  public void removeTaskStatus(TaskStatus ts){
    taskStatuses.remove(ts);
    ts.setEvent(null);
    ts.getPerson().removeTaskStatus(ts);
  }
  
//  public EventForm mapToEventForm() {
//    return EventForm.builder()
//        .id(id)
//        .name(name)
//        .description(description)
//        .topic(topic)
//        .place(place)
//        .dateTime(dateTime)
//        .taskStatuses(taskStatuses.stream().map(x -> x.mapToTaskStatusForm()).collect(Collectors.toList()))
//        .build();
//  }
}
