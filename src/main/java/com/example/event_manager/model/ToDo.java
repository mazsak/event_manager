package com.example.event_manager.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "to_do")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToDo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private boolean predefined = false;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "to_do_id")
  @Singular
  private List<TaskStatus> tasks = new ArrayList<>();

  public void setTaskToTaskStatus(List<Task> tasks) {
    for (Task task : tasks) {
      this.tasks.add(TaskStatus.builder().name(task.getName()).status(false).build());
    }
  }
}
