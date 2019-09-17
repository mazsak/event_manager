package com.example.event_manager.utils;

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
        .filter(b -> b.isPaided() == true)
        .mapToDouble(b -> b.getMoney())
        .sum();
  }

  private void calculateCoastOfAllNotPaided() {
    coastOfAllNotPaided = billingForms
        .stream()
        .filter(b -> b.isPaided() == false)
        .mapToDouble(b -> b.getMoney())
        .sum();
  }

  private void calculateNotPaided() {
    notPaided = billingForms
        .stream()
        .filter(b -> b.isPaided() == false)
        .count();
  }

  private void calculatePaided() {
    paided = billingForms
        .stream()
        .filter(b -> b.isPaided() == true)
        .count();
  }


}
