package com.example.openoff.domain.bookmark.infrastructure;

import com.example.openoff.domain.bookmark.domain.entity.EventBookmark;
import com.example.openoff.domain.bookmark.domain.repository.EventBookmarkRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.openoff.domain.bookmark.domain.entity.QEventBookmark.eventBookmark;


@Slf4j
@Repository
@RequiredArgsConstructor
public class EventBookmarkRepositoryImpl implements EventBookmarkRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public Page<EventBookmark> findMyBookmarkEvent(String userId, Long bookmarkId, Pageable pageable) {
        List<EventBookmark> eventBookmarks = queryFactory
                .select(eventBookmark)
                .from(eventBookmark)
                .where(
                        eventBookmark.user.id.eq(userId),
                        ltEventBookmarkId(bookmarkId)
                )
                .orderBy(eventBookmark.createdDate.desc())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Long> countQuery = queryFactory
                .select(eventBookmark.count())
                .from(eventBookmark)
                .where(
                        eventBookmark.user.id.eq(userId),
                        ltEventBookmarkId(bookmarkId)
                );
        return PageableExecutionUtils.getPage(eventBookmarks, pageable, countQuery::fetchOne);
    }

    private BooleanExpression ltEventBookmarkId(Long bookmarkId) {
        return bookmarkId == null ? null : eventBookmark.id.lt(bookmarkId);
    }
}
