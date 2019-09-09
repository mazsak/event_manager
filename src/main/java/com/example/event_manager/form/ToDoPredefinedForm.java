package com.example.event_manager.form;

import com.example.event_manager.model.ToDoPredefined;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToDoPredefinedForm {
  private Long id;
  private String name;

  @Singular private Set<String> tasks;

  public ToDoPredefined mapToToDoPredefined() {
    return ToDoPredefined.builder().id(id).name(name).tasks(tasks).build();
  }
}
