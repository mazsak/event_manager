package com.example.event_manager.model;

import com.example.event_manager.form.BillingForm;
import java.util.List;
import lombok.Getter;

@Getter
public class BillingsSummary {

  private final List<BillingForm> billingForms;
  private long notPaid;
  private long paid;
  private double coastOfAllPaid;
  private double coastOfAllNotPaid;

  public BillingsSummary(final List<BillingForm> billingForms) {
    this.billingForms = billingForms;
    calculate();
  }

  private void calculate() {
    calculateCoastOfAllPaid();
    calculateCoastOfAllNotPaid();
    calculatePaid();
    calculateNotPaid();

  }

  private void calculateCoastOfAllPaid() {
    coastOfAllPaid = billingForms
        .stream()
        .filter(BillingForm::isPaid)
        .mapToDouble(BillingForm::getMoney)
        .sum();
  }

  private void calculateCoastOfAllNotPaid() {
    coastOfAllNotPaid = billingForms
        .stream()
        .filter(b -> !b.isPaid())
        .mapToDouble(BillingForm::getMoney)
        .sum();
  }

  private void calculateNotPaid() {
    notPaid = billingForms
        .stream()
        .filter(b -> !b.isPaid())
        .count();
  }

  private void calculatePaid() {
    paid = billingForms
        .stream()
        .filter(BillingForm::isPaid)
        .count();
  }
}
