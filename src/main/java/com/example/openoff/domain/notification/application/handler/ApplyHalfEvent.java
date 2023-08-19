package com.example.openoff.domain.notification.application.handler;

import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyHalfEvent {
    private EventIndex eventIndex;
}
