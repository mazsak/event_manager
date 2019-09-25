package com.example.event_manager.utils;

import com.example.event_manager.form.PersonForm;
import com.example.event_manager.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPersonForm implements Converter<String, PersonForm> {

  @Autowired
  private PersonService personService;

  @Override
  public PersonForm convert(final String arg0) {
    return personService.findById(Long.valueOf(arg0));
  }
}
