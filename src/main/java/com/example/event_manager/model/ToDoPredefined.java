package com.example.event_manager.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "to_do_predefined")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToDoPredefined {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ElementCollection
  @CollectionTable(name = "task", joinColumns = @JoinColumn(name = "id"))
  @Column(name = "description")
  @Singular
  private Set<String> tasks;
}
