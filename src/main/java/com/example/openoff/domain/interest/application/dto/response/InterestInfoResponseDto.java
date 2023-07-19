package com.example.openoff.domain.interest.application.dto.response;

import com.example.openoff.domain.interest.domain.entity.FieldType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestInfoResponseDto {
    private String interestConstName;
    private String interestCode;
    private String interestValue;

    @Builder
    public InterestInfoResponseDto(String interestConstName, String interestCode, String interestValue) {
        this.interestConstName = interestConstName;
        this.interestCode = interestCode;
        this.interestValue = interestValue;
    }

    public static InterestInfoResponseDto from(FieldType.InterestTypeInfo interestTypeInfo){
        return InterestInfoResponseDto.builder()
                .interestConstName(interestTypeInfo.getConstName())
                .interestCode(interestTypeInfo.getCode())
                .interestValue(interestTypeInfo.getValue())
                .build();
    }
}
