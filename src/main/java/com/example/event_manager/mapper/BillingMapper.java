package com.example.event_manager.mapper;

import com.example.event_manager.form.BillingForm;
import com.example.event_manager.model.Billing;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillingMapper {

  List<BillingForm> toDtos(List<Billing> billings);

  List<Billing> toEntities(List<BillingForm> billingForms);

  Billing toEntity(BillingForm billingForm);

  BillingForm toDto(Billing billing);
}
