package com.example.event_manager.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EventAddEditForm {

    private EventForm event;
    private List<ToDoPredefinedSimpleForm> predefinedNameList;
    private List<PersonForm> people;

    private int position;

    private List<Boolean> collapses = Arrays.asList(true, false, false, false, false);
}
