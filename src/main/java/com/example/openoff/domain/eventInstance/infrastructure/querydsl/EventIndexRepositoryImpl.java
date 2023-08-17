package com.example.openoff.domain.eventInstance.infrastructure.querydsl;

import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.eventInstance.domain.entity.QEventIndex;
import com.example.openoff.domain.eventInstance.domain.repository.EventIndexRepositoryCustom;
import com.example.openoff.domain.eventInstance.infrastructure.dto.EventIndexStatisticsDto;
import com.example.openoff.domain.ladger.domain.entity.QEventApplicantLadger;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
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
    public List<EventIndexStatisticsDto> statisticsEventIndexByEventInfoId(Long eventInfoId, String userId) {
        QEventApplicantLadger applicantLadger = QEventApplicantLadger.eventApplicantLadger;
        return queryFactory
                .select(
                        Projections.fields(
                                EventIndexStatisticsDto.class,
                                QEventIndex.eventIndex.id.as("eventIndexId"),
                                ExpressionUtils.as(
                                    JPAExpressions
                                            .select(applicantLadger.count().castToNum(Integer.class))
                                            .from(applicantLadger)
                                            .where(
                                                    applicantLadger.eventIndex.id.eq(QEventIndex.eventIndex.id),
                                                    applicantLadger.isAccept.isTrue()
                                            ), "approvedUserCount"),
                                QEventIndex.eventIndex.eventDate.as("eventDate"),
                                ExpressionUtils.as(
                                        JPAExpressions
                                                .selectOne()
                                                .from(applicantLadger)
                                                .where(
                                                        applicantLadger.eventIndex.id.eq(QEventIndex.eventIndex.id)
                                                                .and(applicantLadger.eventApplicant.id.eq(userId))
                                                )
                                                .exists(), "isApply")
                        )
                )
                .from(QEventIndex.eventIndex)
                .where(
                        QEventIndex.eventIndex.eventInfo.id.eq(eventInfoId),
                        QEventIndex.eventIndex.isClose.eq(false)
                )
                .orderBy(QEventIndex.eventIndex.createdDate.asc())
                .fetch();
    }

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
