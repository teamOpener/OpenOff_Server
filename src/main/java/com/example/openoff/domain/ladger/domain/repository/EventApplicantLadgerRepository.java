package com.example.openoff.domain.ladger.domain.repository;

import com.example.openoff.domain.ladger.domain.entity.EventApplicantLadger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventApplicantLadgerRepository extends JpaRepository<EventApplicantLadger, Long>, EventApplicantLadgerRepositoryCustom {
    long countByEventIndex_IdAndIsAcceptTrue(Long eventIndexId);
    List<EventApplicantLadger> findAllByEventApplicant_IdAndEventInfo_Id(String userId, Long eventInfoId);
    List<EventApplicantLadger> findAllByEventApplicant_IdAndEventInfo_IdIn(String userId, List<Long> eventInfoIds);
    Optional<EventApplicantLadger> findByIdAndEventApplicant_Id(Long ladgerId, String userId);
    Optional<EventApplicantLadger> findByEventApplicant_IdAndEventIndex_IdAndTicketIndex(String userId, Long eventIndexId, String ticketIndex);

    Boolean existsByEventIndex_IdAndEventApplicant_Id(Long eventIndexId, String userId);
}
