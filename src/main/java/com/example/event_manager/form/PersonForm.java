package com.example.event_manager.form;

import com.example.event_manager.model.Person;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
public class PersonForm {
  private Long id;
  private String name;

  @Singular private Set<TaskStatusForm> taskStatuses;

  public Person mapToPerson() {
    return Person.builder()
        .id(id)
        .name(name)
        .taskStatuses(taskStatuses.stream().map(x -> x.mapToTaskStatus()).collect(Collectors.toList()))
        .build();
  }
}
