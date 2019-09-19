package com.example.event_manager.mapper;

import com.example.event_manager.form.PersonForm;
import com.example.event_manager.model.Person;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

  PersonForm fromEntity(Person person);

  List<PersonForm> personsToPersonsDtos(List<Person> people);

  Person toEntity(PersonForm personForm);
}
