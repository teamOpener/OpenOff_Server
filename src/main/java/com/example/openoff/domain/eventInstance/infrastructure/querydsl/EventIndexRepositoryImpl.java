package com.example.openoff.domain.eventInstance.infrastructure.querydsl;

import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.eventInstance.domain.entity.QEventIndex;
import com.example.openoff.domain.eventInstance.domain.repository.EventIndexRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EventIndexRepositoryImpl implements EventIndexRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public Map<Long, LocalDateTime> findEventDateByEventInfoId(List<Long> eventInfoIdList) {
        QEventIndex qEventIndex = QEventIndex.eventIndex;

        List<Tuple> fetch = queryFactory
                .select(qEventIndex.eventInfo.id, qEventIndex.eventDate.min())
                .from(qEventIndex)
                .where(qEventIndex.eventInfo.id.in(eventInfoIdList))
                .groupBy(qEventIndex.eventInfo.id)
                .fetch();

        return fetch.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(QEventIndex.eventIndex.eventInfo.id),
                        tuple -> tuple.get(QEventIndex.eventIndex.eventDate.min())
                ));
    }

    @Override
    public List<EventIndex> find1DayLeftEventIndex() {
        QEventIndex qEventIndex = QEventIndex.eventIndex;
        LocalDate oneDayAhead = LocalDate.now().plusDays(1);
        return queryFactory
                .select(qEventIndex)
                .from(qEventIndex)
                .where(
                        qEventIndex.eventDate.year().eq(oneDayAhead.getYear())
                                .and(qEventIndex.eventDate.month().eq(oneDayAhead.getMonthValue()))
                                .and(qEventIndex.eventDate.dayOfMonth().eq(oneDayAhead.getDayOfMonth()))
                )
                .fetch();
    }

    @Override
    public List<EventIndex> findDDayLeftEventIndex() {
        QEventIndex qEventIndex = QEventIndex.eventIndex;
        LocalDate now = LocalDate.now();
        return queryFactory
                .select(qEventIndex)
                .from(qEventIndex)
                .where(
                        qEventIndex.eventDate.year().eq(now.getYear())
                                .and(qEventIndex.eventDate.month().eq(now.getMonthValue()))
                                .and(qEventIndex.eventDate.dayOfMonth().eq(now.getDayOfMonth()))
                )
                .fetch();
    }

    @Override
    public List<EventIndex> findNotClosedEventIndex() {
        QEventIndex qEventIndex = QEventIndex.eventIndex;
        return queryFactory
                .select(qEventIndex)
                .from(qEventIndex)
                .where(
                        qEventIndex.isClose.isFalse()
                                .and(qEventIndex.eventDate.after(LocalDateTime.now()))
                )
                .fetch();
    }
}
