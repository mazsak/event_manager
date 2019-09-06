package com.example.event_manager.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "task_status")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatus {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private boolean status;
}
