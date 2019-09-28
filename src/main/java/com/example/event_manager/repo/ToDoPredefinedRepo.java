package com.example.event_manager.repo;

import com.example.event_manager.model.ToDoPredefined;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToDoPredefinedRepo extends JpaRepository<ToDoPredefined, Long> {

  @EntityGraph(value = "graph.ToDoPredefined.task")
  Optional<ToDoPredefined> findById(Long id);

  @EntityGraph(value = "graph.ToDoPredefined.task")
  List<ToDoPredefined> findAllByOrderByNameAsc();

  @EntityGraph(value = "graph.ToDoPredefined.task")
  Page<ToDoPredefined> findAllByOrderByNameAsc(Pageable pageable);

  List<ToDoPredefined> findAllByNameNotIn(List<String> names);
}
