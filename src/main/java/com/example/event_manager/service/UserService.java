package com.example.event_manager.service;

import com.example.event_manager.form.UserForm;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService extends BasicService<UserForm, Long> {
  boolean update(UserForm user);

  UserDetails loadUserByUsername(String login);

  UserForm getPrincipalSimple();

  UserForm getPrincipal();

  boolean checkIsLogin();
}
