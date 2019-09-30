package com.example.event_manager.form;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillingDetailsForm {

  private Long eventId;
  private List<PersonForm> people;
  private List<BillingForm> billingForms;


}
