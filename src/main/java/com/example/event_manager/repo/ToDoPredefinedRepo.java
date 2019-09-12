package com.example.event_manager.repo;

import com.example.event_manager.model.ToDoPredefined;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoPredefinedRepo extends JpaRepository<ToDoPredefined, Long> {
  List<ToDoPredefined> findAllByOrderByNameAsc();
}
