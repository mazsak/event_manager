package com.example.event_manager.form;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ToDoPredefinedForm {

  private Long id;

  @NotNull
  @Size(min = 3, max = 255, message = "Name length must between 3 and 255")
  private String name;

  @Singular
  @NotEmpty(message = "List length must be min 1")
  private List<
      @NotNull @Size(min = 2, max = 255, message = "Task length must between 2 and 255") String>
      tasks;
}
