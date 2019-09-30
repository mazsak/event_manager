package com.example.event_manager.service;

import com.example.event_manager.form.BillingForm;
import com.example.event_manager.mapper.BillingMapper;
import com.example.event_manager.model.Billing;
import com.example.event_manager.repo.BillingRepo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;


@Service
public class BillingServiceImpl
    extends BasicServiceImpI<Billing, BillingForm, BillingRepo, BillingMapper, Long>
    implements BillingService {

  public BillingServiceImpl(final BillingRepo billingRepo, final BillingMapper mapper) {
    super(billingRepo, mapper);
  }

  @Override
  public void changeState(final Long id) {
    repo.changeState(id);
  }

  @Override
  public List<BillingForm> getAvailableBillings(List<BillingForm> old) {
    return old
        .stream()
        .filter(b -> b.isDeleted() == false)
        .collect(Collectors.toList());
  }

  @Override
  public List<BillingForm> getDeletedBillings(List<BillingForm> old) {
    return old
        .stream()
        .filter(b -> b.isDeleted() == true)
        .collect(Collectors.toList());
  }


}
