package com.example.event_manager.model;

import com.example.event_manager.form.BillingForm;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BillingRaportSchema {

  private String eventName;
  private List<BillingForm> billings;

}
