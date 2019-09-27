package com.example.event_manager.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserForm {
  private Long id;

  @NotNull
  @Size(min = 2, max = 20, message = "Name length must be between 2 and 20")
  private String login;

  @NotNull
  @Size(min = 8, max = 20, message = "Password length must be between 8 and 20")
  private String password;
}
