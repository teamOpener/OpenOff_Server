package com.example.openoff.domain.comment.infrastructure;

import com.example.openoff.domain.comment.domain.entity.EventComment;
import com.example.openoff.domain.comment.domain.repository.EventCommentRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.openoff.domain.comment.domain.entity.QEventComment.eventComment;

@Repository
@RequiredArgsConstructor
public class EventCommentRepositoryImpl implements EventCommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<EventComment> getParentEventComments(Long eventInfoId, Long commentId, Pageable pageable) {
        BooleanExpression conditions = commonConditions(eventInfoId, commentId);

        List<EventComment> eventComments = queryFactory
                .select(eventComment)
                .from(eventComment)
                .where(conditions)
                .orderBy(eventComment.id.asc())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(eventComment.count())
                .from(eventComment)
                .where(conditions);

        return PageableExecutionUtils.getPage(eventComments, pageable, countQuery::fetchOne);
    }

    private BooleanExpression commonConditions(Long eventInfoId, Long commentId) {
        BooleanExpression conditions = eventComment.eventInfo.id.eq(eventInfoId)
                .and(eventComment.parent.isNull());

        if (commentId != null) {
            conditions = conditions.and(eventComment.id.gt(commentId));
        }

        return conditions;
    }

}
