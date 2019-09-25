package com.example.event_manager.mapper;

import com.example.event_manager.form.BillingForm;
import com.example.event_manager.model.Billing;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillingMapper extends BasicMapper<Billing, BillingForm> {}
