package com.example.event_manager.repo;

import com.example.event_manager.model.Event;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {

  @EntityGraph(value = "graph.Event.elements")
  Optional<Event> findById(Long id);

  List<Event> findAllByDateTimeIsBefore(LocalDateTime localDateTime);

  List<Event> findAllByDateTimeIsAfterAndStartedIsTrue(LocalDateTime localDateTime);

  List<Event> findAllByDateTimeIsAfterAndStartedIsFalse(LocalDateTime localDateTime);

  @Query(
          "SELECT e FROM Event e WHERE e.name LIKE %:name% OR e.place LIKE %:name% OR e.topic LIKE %:name%")
  List<Event> searchByNamePlaceTopic(@Param("name") String name);

  @Modifying
  @Transactional
  @Query(
          "UPDATE Event e"
                  + " SET e.started ="
                  + " CASE e.started"
                  + " WHEN true THEN false"
                  + " WHEN false THEN true"
                  + " else e.started END"
                  + " WHERE e.id = :id")
  void changeStarted(Long id);
}
