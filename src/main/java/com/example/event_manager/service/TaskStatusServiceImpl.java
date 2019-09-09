package com.example.event_manager.service;

import com.example.event_manager.model.TaskStatus;
import com.example.event_manager.repo.TaskStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {
  
  private final TaskStatusRepo taskStatusRepo;
  
  @Autowired
  public TaskStatusServiceImpl(TaskStatusRepo taskStatusRepo){
    this.taskStatusRepo = taskStatusRepo;
  }
  
  @Override
  public boolean save(TaskStatus taskStatus) { return taskStatusRepo.save(taskStatus) != null; }
  
  @Override
  public void delete(Long id) { taskStatusRepo.deleteById(id);}
  
  @Override
  public List<TaskStatus> findAll() {
    return taskStatusRepo.findAll();
  }
  
  @Override
  public TaskStatus findById(Long id) {
    return taskStatusRepo.findById(id).get();
  }
}
