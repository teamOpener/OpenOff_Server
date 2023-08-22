package com.example.openoff.domain.user.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.auth.application.dto.response.SocialAccountInfoResponseDto;
import com.example.openoff.domain.auth.application.service.sms.NCPSmsService;
import com.example.openoff.domain.auth.domain.entity.SocialAccount;
import com.example.openoff.domain.auth.domain.service.SocialAccountService;
import com.example.openoff.domain.user.application.dto.request.UserFcmTokenUploadRequestDto;
import com.example.openoff.domain.user.application.dto.request.UserOnboardingRequestDto;
import com.example.openoff.domain.user.application.dto.request.UserSmsCheckRequestDto;
import com.example.openoff.domain.user.application.dto.response.CheckNicknameResponseDto;
import com.example.openoff.domain.user.application.dto.response.SearchNicknameResponseDto;
import com.example.openoff.domain.user.application.dto.response.UserInfoResponseDto;
import com.example.openoff.domain.user.application.dto.response.UserTotalInfoResponseDto;
import com.example.openoff.domain.user.domain.entity.User;
import com.example.openoff.domain.user.domain.exception.UserException;
import com.example.openoff.domain.user.domain.exception.UserNotCorrectSMSNumException;
import com.example.openoff.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@DomainService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserQueryService {
    @Value("${tester.phoneNum}")
    private String testerPhoneNum;
    private final UserUtils userUtils;
    private final NCPSmsService ncpSmsService;
    private final SocialAccountService socialAccountService;
    private final UserFcmTokenService userFcmTokenService;
    private final UserRepository userRepository;

    public User initUserSaveOrFind(SocialAccount socialAccount, String socialType) {
        return userRepository.findBySocialId(socialAccount.getId())
                .orElseGet(() -> {
                    switch (socialType){
                        case "kakao":
                            return userRepository.save(User.builder().kakaoAccount(socialAccount).isActive(true).build());
                        case "google":
                            return userRepository.save(User.builder().googleAccount(socialAccount).isActive(true).build());
                        case "apple":
                            return userRepository.save(User.builder().appleAccount(socialAccount).isActive(true).build());
                        case "normal":
                            return userRepository.save(User.builder().normalAccount(socialAccount).isActive(true).build());
                        default:
                            throw UserException.of(Error.OAUTH_FAILED);
                    }
                });
    }

    public ResponseDto<UserTotalInfoResponseDto> getMyInfo() {
        User user = userUtils.getUser();
        List<SocialAccount> socialAccountList = Stream.of(
                        Optional.ofNullable(user.getKakaoAccount()),
                        Optional.ofNullable(user.getGoogleAccount()),
                        Optional.ofNullable(user.getAppleAccount()),
                        Optional.ofNullable(user.getNormalAccount()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        UserInfoResponseDto userInfoResponseDto = UserInfoResponseDto.from(user);
        List<SocialAccountInfoResponseDto> allSocialAccountInfoList = socialAccountService.getSocialAccountInfos(socialAccountList);
        return ResponseDto.of(HttpStatus.OK.value(), "GET USER INFO SUCCESS", UserTotalInfoResponseDto.from(userInfoResponseDto, allSocialAccountInfoList));
    }

    public ResponseDto<UserInfoResponseDto> getUserInfo(String uuid) {
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> UserException.of(Error.USER_NOT_FOUND));
        return ResponseDto.of(HttpStatus.OK.value(), "GET USER INFO SUCCESS", UserInfoResponseDto.from(user));
    }

    @Transactional
    public ResponseDto<UserInfoResponseDto> updateOnboardingData(UserOnboardingRequestDto userOnboardingRequestDto) {
        User user = userUtils.getUser();
        if (userRepository.existsByNickname(userOnboardingRequestDto.getNickname())) {
            throw UserException.of(Error.USER_NICKNAME_DUPLICATION);
        }
        user.updateBasicUserInfo(userOnboardingRequestDto.getUsername(), userOnboardingRequestDto.getNickname(),
                userOnboardingRequestDto.getYear(), userOnboardingRequestDto.getMonth(), userOnboardingRequestDto.getDay(),
                userOnboardingRequestDto.getGender());
        return ResponseDto.of(HttpStatus.OK.value(), "SUCCESS", UserInfoResponseDto.from(user));
    }

    @Transactional
    public ResponseDto<UserInfoResponseDto> checkSmsNum(UserSmsCheckRequestDto userSmsCheckRequestDto) {
        Optional<User> byPhoneNumber = userRepository.findByPhoneNumber(userSmsCheckRequestDto.getPhoneNum());
        User user = userUtils.getUser();

        if (userSmsCheckRequestDto.getPhoneNum().equals(testerPhoneNum)) { // TESTER 휴대폰 인증을 위한 코드
            user.updatePhoneNumber(userSmsCheckRequestDto.getPhoneNum());
            return ResponseDto.of(HttpStatus.OK.value(), "휴대폰 인증에 성공하였습니다.", UserInfoResponseDto.from(user));
        }

        if (byPhoneNumber.isPresent() && !byPhoneNumber.get().getId().equals(user.getId())) {
            throw UserException.of(Error.USER_PHONE_NUM_DUPLICATION);
        }
        if(ncpSmsService.checkSmsNum(userSmsCheckRequestDto)) {
            user.updatePhoneNumber(userSmsCheckRequestDto.getPhoneNum());
        } else {
            throw new UserNotCorrectSMSNumException(Error.USER_NOT_CORRECT_SMS_NUM);
        }
        return ResponseDto.of(HttpStatus.OK.value(), "휴대폰 인증에 성공하였습니다.", UserInfoResponseDto.from(user));
    }

    public ResponseDto<Void> noLoginCheckSmsNum(UserSmsCheckRequestDto userSmsCheckRequestDto) {
        if(!ncpSmsService.checkSmsNum(userSmsCheckRequestDto)) {
            throw new UserNotCorrectSMSNumException(Error.USER_NOT_CORRECT_SMS_NUM);
        }
        return ResponseDto.of(HttpStatus.OK.value(), "휴대폰 인증에 성공하였습니다.", null);
    }

    public ResponseDto<CheckNicknameResponseDto> checkNicknameExist(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            return ResponseDto.of(HttpStatus.OK.value(), "SUCCESS", CheckNicknameResponseDto.builder().isExist(true).build());
        }
        return ResponseDto.of(HttpStatus.OK.value(), "SUCCESS", CheckNicknameResponseDto.builder().isExist(false).build());
    }

    @Transactional
    public ResponseDto<UserInfoResponseDto> approvalAgreement() {
        User user = userUtils.getUser();
        user.updateTermsConditionsAgreement(true);
        userRepository.save(user);
        return ResponseDto.of(HttpStatus.OK.value(), "SUCCESS", UserInfoResponseDto.from(user));
    }

    @Transactional
    public ResponseDto<UserInfoResponseDto> uploadProfile(String profileUrl) {
        User user = userUtils.getUser();
        user.updateProfileImageUrl(profileUrl);
        userRepository.save(user);
        return ResponseDto.of(HttpStatus.OK.value(), "SUCCESS", UserInfoResponseDto.from(user));
    }

    public ResponseDto<List<SearchNicknameResponseDto>> searchUserByNickname(String keyword) {
        List<SearchNicknameResponseDto> nicknameResponseDtos = userRepository.findByNickname(keyword).stream()
                .map(user -> SearchNicknameResponseDto.builder()
                        .userId(user.getId())
                        .username(user.getUserName())
                        .nickname(user.getNickname())
                        .profileImageUrl(user.getProfileImageUrl()).build()).collect(Collectors.toList());
        return ResponseDto.of(HttpStatus.OK.value(), "SUCCESS", nicknameResponseDtos);
    }

    @Transactional
    public ResponseDto<UserInfoResponseDto> permitAlert(UserFcmTokenUploadRequestDto userFcmTokenUploadRequestDto) {
        User user = userUtils.getUser();
        userFcmTokenService.save(user, userFcmTokenUploadRequestDto);
        return ResponseDto.of(HttpStatus.OK.value(), "SUCCESS", UserInfoResponseDto.from(user));
    }
}
