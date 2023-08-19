package com.example.openoff.domain.eventInstance.domain.repository;

import com.example.openoff.domain.eventInstance.domain.entity.EventExtraAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventExtraAnswerRepository extends JpaRepository<EventExtraAnswer, Long> {
    List<EventExtraAnswer> findAllByEventIndex_Id(Long eventIndexId);
}
