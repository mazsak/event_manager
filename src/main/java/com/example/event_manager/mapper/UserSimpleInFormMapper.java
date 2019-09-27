package com.example.event_manager.mapper;

import com.example.event_manager.form.UserForm;
import com.example.event_manager.form.UserSimpleForm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSimpleInFormMapper extends BasicMapper<UserSimpleForm, UserForm> {}
