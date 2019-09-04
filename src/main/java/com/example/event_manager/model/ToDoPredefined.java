package com.example.event_manager.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToDoPredefined {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  private String name;

  @ElementCollection
  @CollectionTable(name = "task", joinColumns = @JoinColumn(name = "id"))
  @Column(name = "name")
  @Singular
  private List<String> tasks = new ArrayList<>();

}
