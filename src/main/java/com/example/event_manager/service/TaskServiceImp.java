package com.example.event_manager.service;

import com.example.event_manager.model.Task;
import com.example.event_manager.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImp implements TaskService {

  @Autowired private TaskRepo taskRepo;

  @Override
  public boolean save(Task task) {
    Task save = taskRepo.save(task);
    return save != null;
  }

  @Override
  public void delete(Long id) {
    taskRepo.deleteById(id);
  }

  @Override
  public List<Task> findAll() {
    return taskRepo.findAll();
  }

  @Override
  public Task findById(Long id) {
    return taskRepo.findById(id).get();
  }
}
