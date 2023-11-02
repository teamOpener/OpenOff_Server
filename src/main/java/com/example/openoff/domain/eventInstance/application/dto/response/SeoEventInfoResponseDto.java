package com.example.openoff.domain.eventInstance.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SeoEventInfoResponseDto {
    private Long eventId;
    private String title;
    private String streetLoadAddress;
    private String detailAddress;
    private String imageUrl;
    private List<LocalDateTime> eventDateList;

    @Builder
    public SeoEventInfoResponseDto(Long eventId, String title, String streetLoadAddress,
        String detailAddress, String imageUrl, List<LocalDateTime> eventDateList) {
        this.eventId = eventId;
        this.title = title;
        this.streetLoadAddress = streetLoadAddress;
        this.detailAddress = detailAddress;
        this.imageUrl = imageUrl;
        this.eventDateList = eventDateList;
    }
}
