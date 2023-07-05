package com.example.openoff.domain.auth.domain.repository;

import com.example.openoff.domain.auth.domain.entity.SocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {
    Optional<SocialAccount> findBySocialId(String socialId);
}
