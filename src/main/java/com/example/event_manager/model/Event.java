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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  private String name;
  private String description;
  private String topic;
  private String place;
  private LocalDateTime dateTime;

  @ElementCollection
  @CollectionTable(name = "lecturer", joinColumns = @JoinColumn(name = "id"))
  @Column(name = "name")
  @Singular
  private List<String> lecturers;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "todo_id", referencedColumnName = "id")
  private ToDo toDo;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "done_id", referencedColumnName = "id")
  private ToDo done;
}
