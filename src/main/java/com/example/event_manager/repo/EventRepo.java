package com.example.event_manager.repo;

import com.example.event_manager.model.Event;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {

  List<Event> findAllByDateTimeIsBefore(LocalDateTime localDateTime);

  List<Event> findAllByDateTimeIsAfterAndStartedIsTrue(LocalDateTime localDateTime);

  List<Event> findAllByDateTimeIsAfterAndStartedIsFalse(LocalDateTime localDateTime);

  @Query("SELECT e FROM Event e WHERE e.name LIKE %:name% OR e.place LIKE %:name% OR e.topic LIKE %:name%")
  List<Event> searchByNamePlaceTopic(@Param("name") String name);

}
