package com.example.event_manager.utils;

import com.example.event_manager.form.ToDoPredefinedSimpleForm;
import com.example.event_manager.service.ToDoPredefinedService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StringToToDoPredefinedSimpleForm
        implements Converter<String, ToDoPredefinedSimpleForm> {

  private final ToDoPredefinedService toDoPredefinedService;

  @Override
  public ToDoPredefinedSimpleForm convert(final String arg0) {
    return toDoPredefinedService.findByIdSimple(Long.valueOf(arg0));
  }
}
