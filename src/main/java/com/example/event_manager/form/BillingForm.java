package com.example.event_manager.form;

import com.example.event_manager.model.BillingType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BillingForm implements Comparable<BillingForm> {

  private Long id;
  @Size(min = 3, max = 255, message = "Title length between 3 and 255")
  private String title;
  @Min(value = 0, message = "expense > 0")
  private BigDecimal money;

  @Getter
  @Setter
  private boolean confirmed;
  private boolean deleted;
  private PersonForm personAssigned;
  private BillingType billingType;


  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime dateOfCreation;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime dateOfEdition;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime dateOfConfirm;


  @Override
  public int compareTo(BillingForm o) {
    if (getDateOfCreation() == null || o.getDateOfCreation() == null) {
      return 0;
    }
    return getDateOfCreation().compareTo(o.getDateOfCreation());
  }


}
