package com.example.event_manager.form;

import lombok.*;

import java.util.Set;

@ToString
//@Getter
//@Setter
@Builder
@NoArgsConstructor
public class PersonForm {
  private Long id;
  private String name;

  @Singular private Set<TaskStatusForm> taskStatuses;

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setTaskStatuses(Set<TaskStatusForm> taskStatuses) {
    this.taskStatuses = taskStatuses;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Set<TaskStatusForm> getTaskStatuses() {
    return taskStatuses;
  }

  public PersonForm(Long id, String name, Set<TaskStatusForm> taskStatuses) {
    this.id = id;
    this.name = name;
    this.taskStatuses = taskStatuses;
  }
}
