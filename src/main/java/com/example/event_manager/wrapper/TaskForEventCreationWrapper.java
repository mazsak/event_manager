package com.example.event_manager.wrapper;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class TaskForEventCreationWrapper {

  private Long eventId;
  private String name;
  private Long personId;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime date;

  public TaskForEventCreationWrapper(Long eventId) {
    this.eventId = eventId;
  }

  public Long getEventId() {
    return eventId;
  }
}
