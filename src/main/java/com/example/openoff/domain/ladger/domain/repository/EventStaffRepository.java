package com.example.openoff.domain.ladger.domain.repository;

import com.example.openoff.domain.ladger.domain.entity.EventStaff;
import com.example.openoff.domain.ladger.domain.entity.StaffType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventStaffRepository extends JpaRepository<EventStaff, Long>, EventStaffRepositoryCustom {
    boolean existsByEventInfo_IdAndStaff_Id(Long eventInfoId, String staffId);
    List<EventStaff> findEventStaffByEventInfo_Id(Long eventInfoId);
    Optional<EventStaff> findEventStaffByEventInfo_IdAndStaff_Id(Long eventInfoId, String userId);
    void deleteEventStaffByStaff_IdAndEventInfo_IdAndStaffType(String userId, Long eventInfoId, StaffType staffType);
    List<EventStaff> findAllByStaff_Id(String userId);
}
