package com.example.event_manager.model;

import com.example.event_manager.form.PersonForm;
import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
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

  @OneToMany @Singular private Set<TaskStatus> taskStatuses = new HashSet<>();
  public void addTaskStatus(TaskStatus ts){
    this.taskStatuses.add(ts);
    ts.setPerson(this);
  }
  public void removeTaskStatus(TaskStatus ts){
    taskStatuses.remove(ts);
    ts.setPerson(null);
  }

  private String name;

  public PersonForm mapToPersonForm() {
    return PersonForm.builder()
        .id(id)
        .name(name)
        .taskStatuses(taskStatuses.stream().map(x -> x.mapToTaskStatusForm()).collect(Collectors.toList()))
        .build();
  }
}
