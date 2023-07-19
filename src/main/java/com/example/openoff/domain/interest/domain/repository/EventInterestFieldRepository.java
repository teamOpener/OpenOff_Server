package com.example.openoff.domain.interest.domain.repository;

import com.example.openoff.domain.interest.domain.entity.EventInterestField;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventInterestFieldRepository extends JpaRepository<EventInterestField, Long> {
    boolean existsByEventInfo_IdAndFieldType(Long id, FieldType fieldType);
}
