package com.example.openoff.domain.eventInstance.domain.repository;

import com.example.openoff.domain.eventInstance.domain.entity.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventImageRepository extends JpaRepository<EventImage, Long>, EventImageRepositoryCustom {
}
