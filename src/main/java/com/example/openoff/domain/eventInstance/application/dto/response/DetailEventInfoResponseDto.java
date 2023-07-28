package com.example.openoff.domain.eventInstance.application.dto.response;

import com.example.openoff.domain.eventInstance.infrastructure.dto.EventIndexStatisticsDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailEventInfoResponseDto {
    private Long eventId;
    private String title;
    private String streetLoadAddress;
    private String detailAddress;
    private Integer eventFee;
    private Integer maxCapacity;
    private String description;
    private List<ImageInfo> imageList;
    private List<IndexInfo> indexList;

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

    @Builder
    public DetailEventInfoResponseDto(Long eventId, String title, String streetLoadAddress, String detailAddress, Integer eventFee, String description, Integer maxCapacity, List<ImageInfo> imageList, List<IndexInfo> indexList) {
        this.eventId = eventId;
        this.title = title;
        this.streetLoadAddress = streetLoadAddress;
        this.detailAddress = detailAddress;
        this.eventFee = eventFee;
        this.description = description;
        this.maxCapacity = maxCapacity;
        this.imageList = imageList;
        this.indexList = indexList;
    }
}
