package com.example.event_manager.form;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EventAddEditForm {

  @Valid
  private EventForm event;
  private List<ToDoPredefinedSimpleForm> predefinedNameList;
  private List<PersonForm> people;

  private int position;

  private List<Boolean> collapses = Arrays.asList(true, false, false, false, false);
}
