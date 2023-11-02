package com.example.openoff.domain.eventInstance.infrastructure.querydsl;

import com.example.openoff.domain.eventInstance.domain.entity.QEventImage;
import com.example.openoff.domain.eventInstance.domain.repository.EventImageRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventImageRepositoryImpl implements EventImageRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Map<Long, String> findMainImageUrlByEventInfoId(List<Long> eventInfoIdList) {
        QEventImage qEventImage = QEventImage.eventImage;

        List<Tuple> fetch = queryFactory
                .select(qEventImage.eventInfo.id, qEventImage.eventImageUrl)
                .from(qEventImage)
                .where(qEventImage.eventInfo.id.in(eventInfoIdList)
                        .and(qEventImage.isMain.isTrue()))
                .fetch();

        return fetch.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(QEventImage.eventImage.eventInfo.id),
                        tuple -> tuple.get(QEventImage.eventImage.eventImageUrl)
                ));
    }

    @Override
    public String findMainImageUrlByEventInfoId(Long eventInfoId) {
        String imageUrl = queryFactory
            .select(QEventImage.eventImage.eventImageUrl)
            .from(QEventImage.eventImage)
            .where(
                QEventImage.eventImage.eventInfo.id.eq(eventInfoId),
                QEventImage.eventImage.isMain.isTrue()
            ).fetchFirst();
        return imageUrl.isEmpty() ? null : imageUrl;
    }
}
