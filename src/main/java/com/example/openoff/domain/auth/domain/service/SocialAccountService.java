package com.example.openoff.domain.auth.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.util.EncryptionUtils;
import com.example.openoff.domain.auth.application.dto.request.normal.ResetPasswordRequestDto;
import com.example.openoff.domain.auth.application.dto.response.SocialAccountInfoResponseDto;
import com.example.openoff.domain.auth.domain.entity.AccountType;
import com.example.openoff.domain.auth.domain.entity.SocialAccount;
import com.example.openoff.domain.auth.domain.exception.SocialAccountException;
import com.example.openoff.domain.auth.domain.repository.SocialAccountRepository;
import com.example.openoff.domain.user.domain.entity.User;
import com.example.openoff.domain.user.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class SocialAccountService {
    private final EncryptionUtils encryptionUtils;
    private final SocialAccountRepository socialAccountRepository;

    public SocialAccount checkAndSaveSocialAccount(AccountType accountType, String socialId, String email, String socialName) {
        return socialAccountRepository.findBySocialId(socialId)
                .orElseGet(() -> socialAccountRepository.saveAndFlush(SocialAccount.toEntity(accountType, socialId, email, socialName)));
    }

    public Boolean checkExistEmailInNormal(String email) {
        return socialAccountRepository.existsByEmailAndAccountType(email, AccountType.NOMAL);
    }

    public SocialAccount checkAndSaveNormalAccount(String password, String email) {
        if (checkExistEmailInNormal(email)) {
            throw SocialAccountException.of(Error.EMAIL_DUPLICATION);
        } else {
            return socialAccountRepository.saveAndFlush(SocialAccount.toEntity(AccountType.NOMAL, encryptionUtils.passwordEncrypt(email, password), email, UUID.randomUUID().toString()));
        }
    }

    public SocialAccount findNormalAccount(String password, String email) {
        return socialAccountRepository.findByEmailAndSocialIdAndAccountType(email, encryptionUtils.passwordEncrypt(email, password), AccountType.NOMAL)
                .orElseThrow(() -> UserNotFoundException.of(Error.USER_NOT_FOUND));
    }

    public List<SocialAccountInfoResponseDto> getSocialAccountInfos(List<SocialAccount> socialAccountList) {
        return SocialAccountInfoResponseDto.ofList(socialAccountList);
    }

    public void resetNormalAccountPassword(User user, ResetPasswordRequestDto resetPasswordRequestDto) {
        SocialAccount normalAccount = user.getNormalAccount();
        String passwordedEncrypt = encryptionUtils.passwordEncrypt(resetPasswordRequestDto.getEmail(), resetPasswordRequestDto.getNewPassword());
        normalAccount.updateNormalAccountSocialId(passwordedEncrypt);
        socialAccountRepository.saveAndFlush(normalAccount);
    }
}
