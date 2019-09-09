package com.example.event_manager.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@ToString
//@Getter
//@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany
    @Singular
    private Set<TaskStatus> taskStatuses;


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTaskStatuses(Set<TaskStatus> taskStatuses) {
        this.taskStatuses = taskStatuses;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<TaskStatus> getTaskStatuses() {
        return taskStatuses;
    }


}
