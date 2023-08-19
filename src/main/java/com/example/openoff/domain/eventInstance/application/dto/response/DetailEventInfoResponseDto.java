package com.example.openoff.domain.eventInstance.application.dto.response;

import com.example.openoff.domain.eventInstance.infrastructure.dto.EventIndexStatisticsDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailEventInfoResponseDto {
    private Long eventId;
    private String title;
    private String streetLoadAddress;
    private String detailAddress;
    private Integer eventFee;
    private Integer maxCapacity;
    private String description;
    private Boolean isBookmarked;
    private Double longitude;
    private Double latitude;
    private LocalDateTime eventApplyStartDate;
    private LocalDateTime eventApplyEndDate;
    private List<ImageInfo> imageList;
    private List<IndexInfo> indexList;
    private List<ExtraQuestionInfo> extraQuestionList;

    @Getter
    @Builder
    public static class ImageInfo {
        private String imageUrl;
        private Boolean isMain;

        public static ImageInfo of(String imageUrl, Boolean isMain) {
            return ImageInfo.builder()
                    .imageUrl(imageUrl)
                    .isMain(isMain)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class IndexInfo {
        private Long eventIndexId;
        private Integer approvedUserCount;
        private LocalDateTime eventDate;
        private Boolean isApply;

        public static List<IndexInfo> of(List<EventIndexStatisticsDto> eventIndexStatisticsDtoList) {
            return eventIndexStatisticsDtoList.stream()
                    .map(eventIndexStatisticsDto -> IndexInfo.builder()
                            .eventIndexId(eventIndexStatisticsDto.getEventIndexId())
                            .approvedUserCount(eventIndexStatisticsDto.getApprovedUserCount())
                            .eventDate(eventIndexStatisticsDto.getEventDate())
                            .isApply(!eventIndexStatisticsDto.getIsApply())
                            .build()
                    )
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @Builder
    public static class ExtraQuestionInfo {
        private Long eventExtraQuestionId;
        private String question;

        public static ExtraQuestionInfo of(Long eventExtraQuestionId, String question) {
            return ExtraQuestionInfo.builder()
                    .eventExtraQuestionId(eventExtraQuestionId)
                    .question(question)
                    .build();
        }
    }

    @Builder
    public DetailEventInfoResponseDto(Long eventId, String title, String streetLoadAddress, String detailAddress,
                                      Integer eventFee, String description, Integer maxCapacity, Boolean isBookmarked,
                                      Double longitude, Double latitude, LocalDateTime eventApplyStartDate, LocalDateTime eventApplyEndDate,
                                      List<ImageInfo> imageList, List<IndexInfo> indexList, List<ExtraQuestionInfo> extraQuestionList) {
        this.eventId = eventId;
        this.title = title;
        this.streetLoadAddress = streetLoadAddress;
        this.detailAddress = detailAddress;
        this.eventFee = eventFee;
        this.description = description;
        this.isBookmarked = isBookmarked;
        this.maxCapacity = maxCapacity;
        this.longitude = longitude;
        this.latitude = latitude;
        this.eventApplyStartDate = eventApplyStartDate;
        this.eventApplyEndDate = eventApplyEndDate;
        this.imageList = imageList;
        this.indexList = indexList;
        this.extraQuestionList = extraQuestionList;
    }
}
