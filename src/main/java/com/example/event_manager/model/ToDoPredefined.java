package com.example.event_manager.model;

import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

@Entity
@Table(name = "to_do_predefined")
@NamedEntityGraph(
    name = "graph.ToDoPredefined.task",
    attributeNodes = @NamedAttributeNode(value = "tasks"))
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

  @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
  @CollectionTable(name = "task", joinColumns = @JoinColumn(name = "id"))
  @Column(name = "description")
  @Singular
  private Set<String> tasks;
}
