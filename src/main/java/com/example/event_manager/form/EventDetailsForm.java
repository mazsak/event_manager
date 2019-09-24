package com.example.event_manager.form;

import com.example.event_manager.model.BillingsSummary;
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
public class EventDetailsForm {

    private EventForm event;
    private BillingsSummary summary;
}
