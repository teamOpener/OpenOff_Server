package com.example.openoff.domain.auth.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.domain.auth.domain.entity.AccountType;
import com.example.openoff.domain.auth.domain.entity.SocialAccount;
import com.example.openoff.domain.auth.domain.repository.SocialAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class SocialAccountService {
    private final SocialAccountRepository socialAccountRepository;

//    @Transactional
    public SocialAccount checkAndSaveSocialAccount(AccountType accountType, String socialId, String email, String socialName) {
        return socialAccountRepository.findBySocialId(socialId)
                .orElseGet(() -> socialAccountRepository.saveAndFlush(SocialAccount.toEntity(accountType, socialId, email, socialName)));
    }

}
