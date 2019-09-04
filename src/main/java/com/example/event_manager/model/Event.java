package com.example.event_manager.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
  private Long Id;

  private String name;
  private String description;
  private String topic;
  private String place;
  private LocalDateTime dateTime;

  @ElementCollection
  @CollectionTable(name = "status", joinColumns = @JoinColumn(name = "id"))
  @MapKeyColumn(name = "key")
  @Column(name = "value")
  @Singular("status")
  private Map<String, Boolean> status;

  @ManyToMany
  @JoinTable(
      name = "event_predefined",
      joinColumns = @JoinColumn(name = "event_id"),
      inverseJoinColumns = @JoinColumn(name = "predefined_id"))
  @Singular
  private List<ToDoPredefined> predefineds;

  @ElementCollection
  @CollectionTable(name = "adhoc", joinColumns = @JoinColumn(name = "id"))
  @Column(name = "name")
  @Singular("adhoc")
  private List<String> adhoc;
}
