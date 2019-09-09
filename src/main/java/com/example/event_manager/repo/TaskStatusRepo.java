package com.example.event_manager.repo;

import com.example.event_manager.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskStatusRepo extends JpaRepository<TaskStatus, Long> {

}
