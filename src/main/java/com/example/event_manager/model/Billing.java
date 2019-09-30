package com.example.event_manager.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Billing {

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Billing billing = (Billing) o;
    return confirmed == billing.confirmed &&
        deleted == billing.deleted &&
        Objects.equals(id, billing.id) &&
        Objects.equals(title, billing.title) &&
        Objects.equals(money, billing.money) &&
        Objects.equals(personAssigned, billing.personAssigned) &&
        Objects.equals(dateOfCreation, billing.dateOfCreation) &&
        Objects.equals(dateOfEdition, billing.dateOfEdition) &&
        Objects.equals(dateOfConfirm, billing.dateOfConfirm) &&
        billingType == billing.billingType;
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(id, title, money, confirmed, deleted, personAssigned, dateOfCreation, dateOfEdition,
            dateOfConfirm, billingType);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private BigDecimal money;
  private boolean confirmed;
  private boolean deleted;

  @ManyToOne(fetch = FetchType.LAZY)
  private Person personAssigned;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime dateOfCreation = LocalDateTime.now();

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime dateOfEdition;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime dateOfConfirm;

  @Enumerated(EnumType.STRING)
  private BillingType billingType;

//  @PreUpdate
//  public void preUpdate() {
//    System.out.println("UPDATE");
//    dateOfEdition = LocalDateTime.now();
//  }
}
