package com.example.openoff.domain.ladger.infrastructure.querydsl;

import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import com.example.openoff.domain.ladger.domain.entity.EventApplicantLadger;
import com.example.openoff.domain.ladger.domain.repository.EventApplicantLadgerRepositoryCustom;
import com.example.openoff.domain.ladger.presentation.SortType;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.openoff.domain.ladger.domain.entity.QEventApplicantLadger.eventApplicantLadger;


@Slf4j
@Repository
@RequiredArgsConstructor
public class EventApplicantLadgerRepositoryImpl implements EventApplicantLadgerRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public Page<EventInfo> findAllApplyInfos(Long eventInfoId, FieldType fieldType, String userId, Pageable pageable) {
        QueryResults<EventInfo> eventInfos = queryFactory
                .select(eventApplicantLadger.eventInfo)
                .from(eventApplicantLadger)
                .where(
                        eventApplicantLadger.eventApplicant.id.eq(userId),
                        ltEventInfoId(eventInfoId),
                        eventInfoMappedField(fieldType)
                )
                .groupBy(eventApplicantLadger.eventInfo.id)
                .orderBy(eventApplicantLadger.eventInfo.createdDate.desc())
                .limit(pageable.getPageSize() + 1)
                .fetchResults();
        boolean hasNext = eventInfos.getResults().size() > pageable.getPageSize();
        if (hasNext) { eventInfos.getResults().remove(eventInfos.getResults().size()-1); }
        return new PageImpl<>(eventInfos.getResults(), pageable, eventInfos.getTotal());
    }

    @Override
    public Page<EventApplicantLadger> findAllByEventIndex_Id(Long eventIndexId, String username, LocalDateTime time, String keyword, SortType sort, Pageable pageable) {
        List<OrderSpecifier<?>> orders = sortApplicant(sort);
        QueryResults<EventApplicantLadger> results = queryFactory
                .select(eventApplicantLadger)
                .from(eventApplicantLadger)
                .where(
                        eventApplicantLadger.eventIndex.id.eq(eventIndexId),
                        ltUsernameAndCreatedDate(username, time),
                        applicantNameLike(keyword)
                )
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    private BooleanExpression ltUsernameAndCreatedDate(String username, LocalDateTime time) {
        if (username == null || time == null) return null;
        return eventApplicantLadger.eventApplicant.userName.gt(username)
                .or(eventApplicantLadger.eventApplicant.userName.eq(username)
                        .and(eventApplicantLadger.createdDate.gt(time)));
    }


    private BooleanExpression applicantNameLike(String keyword) {
        if (keyword == null) return null;
        return eventApplicantLadger.eventApplicant.userName.like("%" + keyword + "%");
    }

    private List<OrderSpecifier<?>> sortApplicant(SortType sort) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        if (sort.equals(SortType.NAME)) {
            orders.add(eventApplicantLadger.eventApplicant.userName.asc());
            orders.add(eventApplicantLadger.createdDate.asc()); // 예를 들어, 이름이 같을 때는 createdDate로 정렬
        } else { // 신청한지 오래된 순
            orders.add(eventApplicantLadger.createdDate.asc());
        }
        return orders;
    }


    private BooleanExpression ltEventInfoId(Long eventInfoId) {
        if (eventInfoId == null) return null;
        return eventApplicantLadger.eventInfo.id.lt(eventInfoId);
    }

    private BooleanExpression eventInfoMappedField(FieldType fieldType) {
        if (fieldType == null) return null;
        return eventApplicantLadger.eventInfo.eventInterestFields.any().fieldType.eq(fieldType);
    }
}
