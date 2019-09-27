package com.example.event_manager.model;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table
@NamedEntityGraph(
    name = "graph.Event.elements",
    attributeNodes = {
        @NamedAttributeNode(value = "billings"),
        @NamedAttributeNode(value = "taskStatuses", subgraph = "graph.TaskStatus.person")
    },
    subgraphs = {
        @NamedSubgraph(
            name = "graph.TaskStatus.person",
            attributeNodes = {@NamedAttributeNode("person")})
    })
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(length = 500)
  private String description;
  private String topic;
  private String place;
  private Boolean started;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime dateTime = LocalDateTime.now();

  @Singular
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<TaskStatus> taskStatuses;

  @Singular
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<Billing> billings;
}
