package com.example.event_manager.repo;

import com.example.event_manager.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventRepo extends JpaRepository<Event, Long> {}
