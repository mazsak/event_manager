package com.example.event_manager.repo;

import com.example.event_manager.model.ToDoPredefined;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoPredefinedRepo extends JpaRepository<ToDoPredefined, Long> {

  List<ToDoPredefined> findAllByOrderByNameAsc();
}
