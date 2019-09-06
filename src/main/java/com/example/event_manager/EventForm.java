package com.example.event_manager;

import com.example.event_manager.model.ToDo;
import com.example.event_manager.model.ToDoPredefined;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventForm {
    private String name;
    private String description;
    private String topic;
    private String place;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime = LocalDateTime.now();

    @Singular
    private List<ToDoPredefined> toDoPredefineds;

    private ToDo adhoc;
}
