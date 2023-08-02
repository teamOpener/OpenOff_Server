package com.example.openoff.domain.ladger.domain.repository;

import com.example.openoff.domain.ladger.domain.entity.EventStaff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventStaffRepository extends JpaRepository<EventStaff, Long> {
    boolean existsByEventInfo_IdAndStaff_Id(Long eventInfoId, String staffId);
    List<EventStaff> findEventStaffByEventInfo_Id(Long eventInfoId);
}
