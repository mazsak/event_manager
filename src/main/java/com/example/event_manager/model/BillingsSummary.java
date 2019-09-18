package com.example.event_manager.model;

import com.example.event_manager.form.BillingForm;
import java.util.List;
import lombok.Getter;

@Getter
public class BillingsSummary {

  private long notPaided;
  private long paided;
  private double coastOfAllPaided;
  private double coastOfAllNotPaided;
  private final List<BillingForm> billingForms;

  public BillingsSummary(final List<BillingForm> billingForms) {
    this.billingForms = billingForms;
    calculate();
  }

  private void calculate() {
    calculateCoastOfAllPaided();
    calculateCoastOfAllNotPaided();
    calculatePaided();
    calculateNotPaided();

  }

  private void calculateCoastOfAllPaided() {
    coastOfAllPaided = billingForms
        .stream()
        .filter(b -> b.isPaided())
        .mapToDouble(BillingForm::getMoney)
        .sum();
  }

  private void calculateCoastOfAllNotPaided() {
    coastOfAllNotPaided = billingForms
        .stream()
        .filter(b -> !b.isPaided())
        .mapToDouble(BillingForm::getMoney)
        .sum();
  }

  private void calculateNotPaided() {
    notPaided = billingForms
        .stream()
        .filter(b -> !b.isPaided())
        .count();
  }

  private void calculatePaided() {
    paided = billingForms
        .stream()
        .filter(b -> b.isPaided())
        .count();
  }


}
