package com.example.event_manager.form;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TaskStatusForm {

  private Long id;

  @Size(min = 3, max = 255, message = "Description must be between  3 and 255")
  private String name;

  @NotNull
  private Boolean status;
  private String taskStatusType;

  @NotNull(message = "Select date and time")
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime date;

  @NotNull
  private PersonForm person;
}
