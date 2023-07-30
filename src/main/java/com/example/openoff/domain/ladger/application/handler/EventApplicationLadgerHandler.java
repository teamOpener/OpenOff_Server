package com.example.openoff.domain.ladger.application.handler;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.domain.ladger.application.service.QRImageCreateService;
import com.example.openoff.domain.ladger.domain.entity.EventApplicantLadger;
import com.example.openoff.domain.ladger.domain.repository.EventApplicantLadgerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class EventApplicationLadgerHandler {
    private final QRImageCreateService qrImageCreateService;
    private final EventApplicantLadgerRepository eventApplicantLadgerRepository;

    @Async
    @TransactionalEventListener
    public void ladgerPermitAndCreateQRImage(EventApplicantLadger eventApplicantLadger) {
        String qrImageUrl = qrImageCreateService.createQRImageAndUploadToS3(eventApplicantLadger.getEventApplicant().getId(), eventApplicantLadger.getTicketIndex());
        eventApplicantLadger.updateIsAcceptAndQrCodeUrl(true, qrImageUrl);
        eventApplicantLadgerRepository.save(eventApplicantLadger);
    }
}
