package com.example.event_manager.wrapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeletingTaskStatusInEventWrapper {
  private Long eventId;
  private Long taskStatusId;
}
