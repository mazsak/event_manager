package com.example.event_manager.model;

import com.example.event_manager.form.PersonForm;
import lombok.*;

import javax.persistence.*;
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
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToMany @Singular private Set<TaskStatus> taskStatuses;

  public PersonForm mapToPersonForm() {
    return PersonForm.builder()
        .id(id)
        .name(name)
        .taskStatuses(taskStatuses.stream().map(x -> x.mapToTaskStatusForm()).collect(Collectors.toList()))
        .build();
  }
}
