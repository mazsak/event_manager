package com.example.event_manager.service;

import com.example.event_manager.model.ToDoPredefined;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoPredefinedServiceImpl implements ToDoPredefinedService {
  @Override
  public boolean save(ToDoPredefined toDoPredefined) {
    return false;
  }
  
  @Override
  public void delete(Long id) {
  
  }
  
  @Override
  public List<ToDoPredefined> findAll() {
    return null;
  }
  
  @Override
  public ToDoPredefined findById(Long id) {
    return null;
  }
}
