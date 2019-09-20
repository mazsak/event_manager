package com.example.event_manager.service;

import com.example.event_manager.form.BillingForm;
import com.example.event_manager.mapper.BillingMapper;
import com.example.event_manager.model.Billing;
import com.example.event_manager.repo.BillingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

  private final BillingRepo billingRepo;
  private final BillingMapper billingMapper;

  @Override
  public boolean save(final BillingForm billing) {
    return billingRepo.save(billingMapper.toEntity(billing)) != null;
  }

  @Override
  public BillingForm saveAndReturn(BillingForm billingForm) {
    return billingMapper.toDto(billingRepo.save(billingMapper.toEntity(billingForm)));
  }

  @Override
  public List<BillingForm> saveAndReturnList(List<BillingForm> billings) {
    return billingMapper.toDtos(billingRepo.saveAll(billingMapper.toEntities(billings)));
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
  public void changeState(Long id) {
    billingRepo.changeState(id);
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
