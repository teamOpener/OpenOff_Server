package com.example.openoff.domain.eventInstance.application.handler;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.eventInstance.domain.repository.EventIndexRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class EventIndexHandler {
    private final EventIndexRepository eventIndexRepository;
    private final EventInfoHandler eventInfoHandler;

    @TransactionalEventListener
    public void eventInfoApplyPermitHandler(EventIndex eventIndex) {
        log.info("eventInfoApplyPermitHandler");
        eventIndex.updateEventIndexIsClose(true);
        eventIndexRepository.save(eventIndex);
        if (eventIndex.getEventInfo().getEventIndexes().stream().map(EventIndex::getIsClose).allMatch(isClose -> isClose.equals(true))) {
            eventInfoHandler.eventInfoApplyPermitHandler(eventIndex.getEventInfo());
        }
    }
}