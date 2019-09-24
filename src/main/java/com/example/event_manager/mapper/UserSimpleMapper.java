package com.example.event_manager.mapper;

import com.example.event_manager.form.UserSimpleForm;
import com.example.event_manager.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSimpleMapper extends BasicMapper<User, UserSimpleForm> {
}
