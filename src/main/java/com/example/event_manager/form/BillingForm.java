package com.example.event_manager.form;

import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class BillingForm {

  private Long id;

  @Size(min = 3, max = 255, message = "Comapny name length between 3 and 255")
  private String companyName;
  @Min(value = 0, message = "expense > 0")
  private double money;
  @NotNull(message = "status not null")
  private boolean paid;
  @Size(min = 3, max = 255, message = "Person must be assigned")
  private String personAssigned;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime paidTime = LocalDateTime.now();

}
