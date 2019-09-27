package com.example.event_manager.repo;

import com.example.event_manager.model.Event;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {

  @EntityGraph(value = "graph.Event.elements")
  Optional<Event> findById(Long id);

  Page<Event> findAllByDateTimeIsAfterAndStartedIsTrue(
      LocalDateTime localDateTime, Pageable pageable);

  Page<Event> findAllByDateTimeIsAfterAndStartedIsFalse(
      LocalDateTime localDateTime, Pageable pageable);

  Page<Event> findAllByDateTimeIsBefore(LocalDateTime now, Pageable pagingO);

  @Query(
      "SELECT e FROM Event e, Event ev WHERE ev.dateTime < :localDateTime AND (LOWER(e.name) LIKE %:name% OR LOWER(e.place) LIKE %:name% OR LOWER(e.topic) LIKE %:name%) AND ev.id = e.id ")
  Page<Event> searchOutdated(String name, LocalDateTime localDateTime, Pageable pageable);

  @Query(
      "SELECT e FROM Event e, Event ev WHERE ev.dateTime >= :localDateTime AND ev.started = TRUE AND (LOWER(e.name) LIKE %:name% OR LOWER(e.place) LIKE %:name% OR LOWER(e.topic) LIKE %:name%) AND ev.id = e.id  ")
  Page<Event> searchStarted(String name, LocalDateTime localDateTime, Pageable pageable);

  @Query(
      "SELECT e FROM Event e, Event ev WHERE ev.dateTime > :localDateTime AND ev.started = FALSE AND (LOWER(e.name) LIKE %:name% OR LOWER(e.place) LIKE %:name% OR LOWER(e.topic) LIKE %:name%) AND ev.id = e.id  ")
  Page<Event> searchNotStarted(String name, LocalDateTime localDateTime, Pageable pageable);

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
