package com.example.event_manager.repo;

import com.example.event_manager.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TaskStatusRepo extends JpaRepository<TaskStatus, Long> {

  List<TaskStatus> findAllByPersonId(Long id);

  @Modifying
  @Transactional
  @Query(
      "UPDATE TaskStatus ts"
          + " SET ts.status ="
          + " CASE ts.status"
          + " WHEN true THEN false"
          + " WHEN false THEN true"
          + " else ts.status END"
          + " WHERE ts.id = :id")
  void changeState(Long id);
}
