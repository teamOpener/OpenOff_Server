package com.example.openoff.domain.notification.application.handler;

import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishApplyHalfEvent(EventIndex eventIndex) {
        applicationEventPublisher.publishEvent(new ApplyHalfEvent(eventIndex));
    }
}
