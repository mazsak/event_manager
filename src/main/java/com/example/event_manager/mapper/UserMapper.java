package com.example.event_manager.mapper;

import com.example.event_manager.form.UserForm;
import com.example.event_manager.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BasicMapper<User, UserForm> {}