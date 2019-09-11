package com.example.event_manager.mapper;

import com.example.event_manager.form.PersonForm;
import com.example.event_manager.model.Person;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PersonMapper {

  PersonForm personToPresonDto(Person person);

  Person toPOJO(PersonForm personForm);
}
