package com.example.event_manager.repo;

import com.example.event_manager.model.ToDoPredefined;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoPredefinedRepo extends JpaRepository<ToDoPredefined, Long> {

  @EntityGraph(value = "graph.ToDoPredefined.task")
  Optional<ToDoPredefined> findById(Long id);

  @EntityGraph(value = "graph.ToDoPredefined.task")
  List<ToDoPredefined> findAllByOrderByNameAsc();

  List<ToDoPredefined> findAllByNameNotIn(List<String> names);
}
