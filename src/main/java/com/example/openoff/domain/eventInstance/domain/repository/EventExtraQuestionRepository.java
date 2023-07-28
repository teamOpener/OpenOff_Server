package com.example.openoff.domain.eventInstance.domain.repository;

import com.example.openoff.domain.eventInstance.domain.entity.EventExtraQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventExtraQuestionRepository extends JpaRepository<EventExtraQuestion, Long> {
    List<EventExtraQuestion> findAllByIdIn(List<Long> extraQuestionIds);
}
