package com.example.openoff.domain.user.domain.repository;

import com.example.openoff.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    @Query(value =
            "select u from User u " +
            "where u.kakaoAccount.id = :socialId or u.googleAccount.id = :socialId or u.appleAccount.id = :socialId or u.normalAccount.id = :socialId")
    Optional<User> findBySocialId(Long socialId);

    Boolean existsByNickname(String nickname);
}
