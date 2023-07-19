package com.example.openoff.domain.eventInstance.domain.repository;

import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventInfoRepository extends JpaRepository<EventInfo, Long> {
}
