package com.example.event_manager.enums;

import org.springframework.data.domain.Sort;

public enum SortHowEnum {

  NAME_ASC(Sort.by("name").ascending()), LOCATION_ASC(Sort.by("place").ascending()), DATE_ASC(
      Sort.by("dateTime").ascending()), TOPIC_ASC(Sort.by("topic").ascending()),
  NAME_DSC(Sort.by("name").descending()), LOCATION_DSC(Sort.by("place").descending()), DATE_DSC(
      Sort.by("dateTime").descending()), TOPIC_DSC(Sort.by("topic").descending());

  public final Sort sort;

  SortHowEnum(final Sort name) {
    this.sort = name;
  }
}
