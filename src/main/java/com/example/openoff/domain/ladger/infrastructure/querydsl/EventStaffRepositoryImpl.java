package com.example.openoff.domain.ladger.infrastructure.querydsl;

import com.example.openoff.domain.ladger.domain.entity.EventStaff;
import com.example.openoff.domain.ladger.domain.repository.EventStaffRepositoryCustom;
import com.example.openoff.domain.user.domain.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.openoff.domain.ladger.domain.entity.QEventStaff.eventStaff;


@Slf4j
@Repository
@RequiredArgsConstructor
public class EventStaffRepositoryImpl implements EventStaffRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<User> findEventStaffUsersByEventInfo_Id(Long eventInfoId) {
        return queryFactory
                .select(eventStaff)
                .from(eventStaff)
                .where(
                        eventStaff.eventInfo.id.eq(eventInfoId)
                )
                .fetch()
                .stream()
                .map(EventStaff::getStaff)
                .collect(Collectors.toList());
    }
}
