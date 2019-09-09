package com.example.event_manager.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

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

  @OneToMany
  @Singular
  private Set<TaskStatus> taskStatuses = new HashSet<>();

  public void addTaskStatus(TaskStatus ts) {
    this.taskStatuses.add(ts);
    ts.setPerson(this);
  }

  public void removeTaskStatus(TaskStatus ts) {
    taskStatuses.remove(ts);
    ts.setPerson(null);
  }

}
