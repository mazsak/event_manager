package com.example.event_manager.model;

import com.example.event_manager.form.ToDoPredefinedForm;
import lombok.*;

import javax.persistence.*;
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

  public ToDoPredefinedForm mapToDoPredefinedForm() {
    return ToDoPredefinedForm.builder().id(id).name(name).tasks(tasks).build();
  }
}
