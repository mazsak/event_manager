package com.example.event_manager.mapper;

import com.example.event_manager.form.PersonForm;
import com.example.event_manager.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {

    PersonMapper INSTAMCE = Mappers.getMapper(PersonMapper.class);

    PersonForm toDto(Person person);
    Person toPOJO(PersonForm personForm);
}
