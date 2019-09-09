package com.example.event_manager.form;

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
}
