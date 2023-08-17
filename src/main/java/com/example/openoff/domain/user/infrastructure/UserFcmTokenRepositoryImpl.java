package com.example.openoff.domain.user.infrastructure;

import com.example.openoff.domain.user.domain.repository.UserFcmTokenRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.openoff.domain.user.domain.entity.QUserFcmToken.userFcmToken;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserFcmTokenRepositoryImpl implements UserFcmTokenRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findAllFcmTokens(List<String> userIds){
        return queryFactory
                .select(userFcmToken.fcmToken)
                .from(userFcmToken)
                .where(userFcmToken.user.id.in(userIds))
                .fetch();
    }
}
