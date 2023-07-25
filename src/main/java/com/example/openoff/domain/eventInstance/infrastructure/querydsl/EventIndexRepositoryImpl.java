package com.example.openoff.domain.eventInstance.infrastructure.querydsl;

import com.example.openoff.domain.eventInstance.domain.entity.QEventIndex;
import com.example.openoff.domain.eventInstance.domain.repository.EventIndexRepositoryCustom;
import com.example.openoff.domain.eventInstance.infrastructure.dto.EventIndexStatisticsDto;
import com.example.openoff.domain.ladger.domain.entity.QEventApplicantLadger;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}
