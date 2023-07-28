package com.example.openoff.domain.eventInstance.infrastructure.querydsl;

import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.domain.eventInstance.application.dto.request.EventSearchRequestDto;
import com.example.openoff.domain.eventInstance.application.dto.response.MainTapEventInfoResponse;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.entity.QEventImage;
import com.example.openoff.domain.eventInstance.domain.entity.QEventIndex;
import com.example.openoff.domain.eventInstance.domain.entity.QEventInfo;
import com.example.openoff.domain.eventInstance.domain.repository.EventInfoRepositoryCustom;
import com.example.openoff.domain.eventInstance.presentation.CapacityRange;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import com.example.openoff.domain.interest.domain.entity.QEventInterestField;
import com.example.openoff.domain.ladger.domain.entity.QEventApplicantLadger;
import com.example.openoff.domain.ladger.domain.entity.QEventStaff;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventInfoRepositoryImpl implements EventInfoRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<EventInfo> searchAllEventInfosInMap(EventSearchRequestDto eventSearchRequestDto) {
        return queryFactory
                .selectDistinct(QEventInfo.eventInfo)
                .from(QEventInfo.eventInfo)
                .leftJoin(QEventInfo.eventInfo.eventStaffs, QEventStaff.eventStaff)
                .leftJoin(QEventInfo.eventInfo.eventInterestFields, QEventInterestField.eventInterestField)
                .leftJoin(QEventInfo.eventInfo.eventImages, QEventImage.eventImage)
                .leftJoin(QEventInfo.eventInfo.eventIndexes, QEventIndex.eventIndex)
                .where(
                        QEventInfo.eventInfo.isApproval.eq(true),
                        distanceJudgment(eventSearchRequestDto.getLatitude(), eventSearchRequestDto.getLongitude()),
                        eventDateJudgment(eventSearchRequestDto.getStartDate(), eventSearchRequestDto.getEndDate()),
                        keywordContains(eventSearchRequestDto.getKeyword()),
                        fieldJudgment(eventSearchRequestDto.getField()),
                        eventFeeJudgment(eventSearchRequestDto.getEventFee()),
                        capacityJudgment(eventSearchRequestDto.getCapacity()),
                        applyableJudgment(eventSearchRequestDto.getApplyable())
                )
                .orderBy(QEventInfo.eventInfo.createdDate.desc())
                .fetch();
    }

    @Override
    public PageResponse<MainTapEventInfoResponse> findMainTapEventInfoByFieldType(FieldType fieldType, final Long eventInfoId, final Pageable pageable) {
        List<MainTapEventInfoResponse> data = queryFactory
                .select(
                        Projections.fields(
                                MainTapEventInfoResponse.class,
                                QEventInfo.eventInfo.id.as("eventInfoId"),
                                QEventInfo.eventInfo.eventTitle.as("eventTitle"),
                                QEventInfo.eventInfo.location.streetNameAddress.as("streetRoadAddress"),
                                QEventInfo.eventInfo.totalRegisterCount.as("totalApplicantCount")
                        )
                )
                .from(QEventInfo.eventInfo)
                .where(
                        QEventInfo.eventInfo.isApproval.eq(true),
                        ltEventInfoId(eventInfoId),
                        eventInfoMappedField(fieldType)
                )
                .orderBy(QEventInfo.eventInfo.createdDate.desc())
                .limit(pageable.getPageSize())
                .fetch();

        return PageResponse.of(new PageImpl<>(data, pageable, data.size()));
    }

    @Override
    public PageResponse<MainTapEventInfoResponse> findMainTapEventInfoByVogue(Long eventInfoId, Integer count, final Pageable pageable) {
        List<MainTapEventInfoResponse> data = queryFactory
                .select(
                        Projections.fields(
                                MainTapEventInfoResponse.class,
                                QEventInfo.eventInfo.id.as("eventInfoId"),
                                QEventInfo.eventInfo.eventTitle.as("eventTitle"),
                                QEventInfo.eventInfo.location.streetNameAddress.as("streetRoadAddress"),
                                QEventInfo.eventInfo.totalRegisterCount.as("totalApplicantCount")
                        )
                )
                .from(QEventInfo.eventInfo)
                .where(
                        QEventInfo.eventInfo.isApproval.eq(true),
                        ltVogueEventInfo(eventInfoId, count)
                )
                .orderBy(QEventInfo.eventInfo.totalRegisterCount.desc(), QEventInfo.eventInfo.createdDate.desc())
                .limit(pageable.getPageSize())
                .fetch();

        return PageResponse.of(new PageImpl<>(data, pageable, data.size()));
    }

    private BooleanExpression ltEventInfoId(Long eventInfoId) {
        if (eventInfoId == null) return null;
        return QEventInfo.eventInfo.id.lt(eventInfoId);
    }

    private BooleanExpression ltVogueEventInfo(Long eventInfoId, Integer count) {
        if (count == null) return null;
        return (QEventInfo.eventInfo.totalRegisterCount.eq(count).and(QEventInfo.eventInfo.id.lt(eventInfoId)))
                .or(QEventInfo.eventInfo.totalRegisterCount.lt(count));
    }

    private BooleanExpression eventInfoMappedField(FieldType fieldType) {
        if (fieldType == null) return null;
        return QEventInfo.eventInfo.eventInterestFields.any().fieldType.eq(fieldType);
    }

    private BooleanExpression distanceJudgment(Double latitude, Double longitude) {
        return Expressions.stringTemplate("ST_Distance_Sphere({0}, {1})",
                Expressions.stringTemplate("POINT({0}, {1})",
                        longitude, latitude
                ),
                Expressions.stringTemplate("POINT({0}, {1})",
                        QEventInfo.eventInfo.location.longitude,
                        QEventInfo.eventInfo.location.latitude
                )
        ).loe(String.valueOf(20000)); // 20km 이내면 true 반환
    }

    private BooleanExpression eventDateJudgment(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) return null;
        return QEventInfo.eventInfo.eventIndexes.any().eventDate.between(startDate, endDate);
    }

    private BooleanExpression keywordContains(String keyword) {
        if (keyword == null) return null;
        return QEventInfo.eventInfo.eventTitle.contains(keyword)
                .or(QEventInfo.eventInfo.location.streetNameAddress.contains(keyword))
                .or(QEventInfo.eventInfo.eventStaffs.any().name.contains(keyword));
    }

    private BooleanExpression fieldJudgment(FieldType field) {
        if (field == null) return null;
        return QEventInfo.eventInfo.eventInterestFields.any().fieldType.eq(field);
    }

    private BooleanExpression eventFeeJudgment(Integer eventFee) {
        if (eventFee == null) {
            return null;
        }
        else if (eventFee.equals(0)) {
            return QEventInfo.eventInfo.eventFee.eq(0);
        } else {
            return QEventInfo.eventInfo.eventFee.ne(0);
        }
    }

    private BooleanExpression applyableJudgment(Boolean applyable) {
        if (applyable == null) {
            return null;
        }
        else if (applyable.equals(true)) {
            return QEventInfo.eventInfo.eventApplyPermit.eq(true);
        } else {
            return QEventInfo.eventInfo.eventApplyPermit.eq(false);
        }
    }

    private BooleanExpression capacityJudgment(CapacityRange capacity) {
        if (capacity == null) return null;
        switch (capacity) {
            case HUGE:
                return QEventInfo.eventInfo.eventMaxPeople.goe(100);
            case LARGE:
                return QEventInfo.eventInfo.eventMaxPeople.lt(100).and(QEventInfo.eventInfo.eventMaxPeople.goe(50));
            case MEDIUM:
                return QEventInfo.eventInfo.eventMaxPeople.lt(50).and(QEventInfo.eventInfo.eventMaxPeople.goe(20));
            case SMALL:
                return QEventInfo.eventInfo.eventMaxPeople.lt(20);
            default:
                return null;
        }
    }

    private OrderSpecifier<Long> createVogueOrderSpec() {
        return new OrderSpecifier<>
                (Order.DESC,
                        JPAExpressions
                                .select(QEventApplicantLadger.eventApplicantLadger.count())
                                .from(QEventApplicantLadger.eventApplicantLadger)
                                .groupBy(QEventApplicantLadger.eventApplicantLadger.eventIndex.id), null);
    }

}