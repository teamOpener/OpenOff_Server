package com.example.openoff.domain.eventInstance.domain.repository;

import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventIndexRepository extends JpaRepository<EventIndex, Long>, EventIndexRepositoryCustom {

  List<EventIndex> findAllByEventInfo_Id(Long eventInfoId);
}
