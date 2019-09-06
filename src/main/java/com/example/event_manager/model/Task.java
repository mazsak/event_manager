package com.example.event_manager.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "task")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
}
