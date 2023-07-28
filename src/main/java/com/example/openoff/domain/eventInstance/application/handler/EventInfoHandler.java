package com.example.openoff.domain.eventInstance.application.handler;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.repository.EventInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class EventInfoHandler {
    private final EventInfoRepository eventinfoRepository;

    @TransactionalEventListener
    public void eventInfoApplyPermitHandler(EventInfo eventInfo) {
        log.info("eventInfoApplyPermitHandler");
        eventInfo.updateApplyPermit(false);
        eventinfoRepository.save(eventInfo);
    }
}
