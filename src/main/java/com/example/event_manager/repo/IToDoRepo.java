package com.example.event_manager.repo;

import com.example.event_manager.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IToDoRepo extends JpaRepository<ToDo, Long> {
    List<ToDo> findByPredefined(boolean perdefined);
}
