package com.example.openoff.domain.ladger.application.dto.response;

import com.example.openoff.domain.interest.domain.entity.FieldType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ApplicationInfoResponseDto {
    private Long eventInfoId;
    private String ticketIndex;
    private List<LocalDateTime> eventDateList;
    private List<FieldType> fieldTypeList;

}
