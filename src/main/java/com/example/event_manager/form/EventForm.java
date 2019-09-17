package com.example.event_manager.form;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EventForm {

  private Long id;
  private String name;
  private String description;
  private String topic;
  private String place;
  private Boolean started;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime dateTime = LocalDateTime.now();
  @Singular
  private List<TaskStatusForm> taskStatuses = new ArrayList<>();

  @Singular
  private List<BillingForm> billings = new ArrayList<>();



}
