package com.example.event_manager.service;

import com.example.event_manager.form.BillingForm;
import com.example.event_manager.mapper.BillingMapper;
import com.example.event_manager.model.Billing;
import com.example.event_manager.repo.BillingRepo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

  private final BillingRepo billingRepo;
  private final BillingMapper billingMapper;

  @Override
  public boolean save(final Billing billing) {
    return billingRepo.save(billing) != null;
  }

  @Override
  public boolean saveBillingForm(final BillingForm billingForm) {
    return save(billingMapper.toEntity(billingForm));
  }

  @Override
  public void delete(final Long id) {
    billingRepo.deleteById(id);
  }

  @Override
  public List<BillingForm> findAll() {
    return billingMapper.toDtos(billingRepo.findAll());
  }

  @Override
  public Billing findById(final Long id) {
    return billingRepo.findById(id).get();
  }

  @Override
  public BillingForm billingFormById(final Long id) {
    return billingMapper.toDto(findById(id));
  }


}
