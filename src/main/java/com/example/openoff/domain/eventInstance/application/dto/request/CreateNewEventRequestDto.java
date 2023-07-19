package com.example.openoff.domain.eventInstance.application.dto.request;

import com.example.openoff.domain.interest.domain.entity.FieldType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateNewEventRequestDto {
    private List<FieldType> fieldTypeList;
    private String title;
    private LocalDateTime applicationStartDate;
    private LocalDateTime applicationEndDate;
    private LocalDateTime eventStartDate;
    private LocalDateTime eventEndDate;
    private String streetLoadAddress;
    private String detailAddress;
    private Integer eventFee;
    private Integer maxParticipant;
    private String description;
    private List<ImageUrlList> imageDataList;
    private List<String> extraQuestionList;
    private String hostName;
    private String hostPhoneNumber;
    private String hostEmail;

    @Getter
    public static class ImageUrlList {
        private String imageUrl;
        private Boolean isMain;
    }
}
