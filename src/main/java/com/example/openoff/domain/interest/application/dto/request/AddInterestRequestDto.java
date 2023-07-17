package com.example.openoff.domain.interest.application.dto.request;

import com.example.openoff.domain.interest.domain.entity.InterestType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddInterestRequestDto {
    private List<InterestType> interestTypeList;
}