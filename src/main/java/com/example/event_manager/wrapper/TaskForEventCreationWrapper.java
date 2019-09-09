package com.example.event_manager.wrapper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskForEventCreationWrapper {
  private Long eventId;
  private String name;
  private Long personId;
  
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime date;
  
  public Long getEventId(){return eventId;}
  
  public TaskForEventCreationWrapper(Long l){
    this.eventId = l;
  }
}
