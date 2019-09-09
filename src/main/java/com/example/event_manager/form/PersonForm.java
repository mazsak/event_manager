package com.example.event_manager.form;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonForm {

  private Long id;
  private String name;

  @Singular
  private Set<TaskStatusForm> taskStatuses;
}
