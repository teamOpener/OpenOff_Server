package com.example.openoff.domain.ladger.infrastructure.querydsl;

import com.example.openoff.domain.ladger.domain.repository.EventApplicantLadgerRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventApplicantLadgerRepositoryImpl implements EventApplicantLadgerRepositoryCustom {
    private final JPAQueryFactory queryFactory;

}
