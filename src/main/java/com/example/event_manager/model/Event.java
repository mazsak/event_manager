package com.example.event_manager.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
  private LocalDateTime dateTime;

  @ManyToMany
  @JoinTable(
      name = "event_to_do",
      joinColumns = @JoinColumn(name = "event_id"),
      inverseJoinColumns = @JoinColumn(name = "to_do_id"))
  @Singular
  private List<ToDo> toDos;
}
