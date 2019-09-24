package com.example.event_manager.repo;

import com.example.event_manager.model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface BillingRepo extends JpaRepository<Billing, Long> {

  @Modifying
  @Transactional
  @Query(
      "UPDATE Billing b"
          + " SET b.paided ="
          + " CASE b.paided"
          + " WHEN true THEN false"
          + " WHEN false THEN true"
          + " else b.paided END"
          + " WHERE b.id = :id")
  void changeState(Long id);
}
