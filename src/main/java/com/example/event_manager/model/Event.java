package com.example.event_manager.model;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private String topic;
  private String place;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime dateTime = LocalDateTime.now();

  @Singular
  @OneToMany(cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private Set<TaskStatus> taskStatuses;

  public void addTaskStatus(TaskStatus ts) {
    taskStatuses.add(ts);
    ts.setEvent(this);
  }

  public void removeTaskStatus(TaskStatus ts) {
    taskStatuses.remove(ts);
    ts.setEvent(null);
    ts.getPerson().removeTaskStatus(ts);
  }

}
