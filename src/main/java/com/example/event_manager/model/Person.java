package com.example.event_manager.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@ToString
@Getter
@Setter
//@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToMany @Singular private Set<TaskStatus> taskStatuses = new HashSet<>();
  public void addTaskStatus(TaskStatus ts){
    this.taskStatuses.add(ts);
    ts.setPerson(this);
  }
  public void removeTaskStatus(TaskStatus ts){
    taskStatuses.remove(ts);
    ts.setPerson(null);
  }

}
