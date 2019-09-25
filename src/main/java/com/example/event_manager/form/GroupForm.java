package com.example.event_manager.form;

import com.example.event_manager.enums.GroupName;
import com.example.event_manager.enums.SortHowEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupForm {

  private GroupName groupName;
  private Page<EventWithoutNestedStuff> pageOfEvents;
  private SortHowEnum sortHowEnum;
  private int pageNumberOfCurrentGroup;
  private int pageSizeOfCurrentGroup;
  private int pageNumberOfSecondGroup;
  private int pageSizeOfSecondGroup;
  private int pageNumberOfThirdGroup;
  private int pageSizeOfThirdGroup;
  private String query;
}
