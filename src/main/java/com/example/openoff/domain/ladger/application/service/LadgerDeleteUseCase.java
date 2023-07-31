package com.example.openoff.domain.ladger.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.ladger.domain.entity.EventApplicantLadger;
import com.example.openoff.domain.ladger.domain.service.EventApplicantLadgerService;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class LadgerDeleteUseCase {
    private final UserUtils userUtils;
    private final EventApplicantLadgerService eventApplicantLadgerService;

    public void deleteMyApplyLadger(Long ladgerId){
        User user = userUtils.getUser();
        EventApplicantLadger myEventTicketInfo = eventApplicantLadgerService.findMyEventTicketInfo(ladgerId, user.getId());
        myEventTicketInfo.getEventInfo().updateTotalRegisterCountMinus();
        eventApplicantLadgerService.deleteEventApplicantLadger(myEventTicketInfo.getId());
    }
}
