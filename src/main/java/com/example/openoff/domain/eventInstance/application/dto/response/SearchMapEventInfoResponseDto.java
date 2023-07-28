package com.example.openoff.domain.eventInstance.application.dto.response;

import com.example.openoff.domain.interest.domain.entity.FieldType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchMapEventInfoResponseDto {
    private Long id;
    private String title;
    private List<FieldType> fieldTypeList;
    private String streetLoadAddress;
    private String detailAddress;
    private Double latitude;
    private Double longitude;
    private List<ImageInfo> imageList;
    private List<LocalDateTime> eventDateList;

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

    @Builder
    public SearchMapEventInfoResponseDto(Long id, String title, List<FieldType> fieldTypeList, String streetLoadAddress, String detailAddress, Double latitude, Double longitude, List<ImageInfo> imageList, List<LocalDateTime> eventDateList) {
        this.id = id;
        this.title = title;
        this.fieldTypeList = fieldTypeList;
        this.streetLoadAddress = streetLoadAddress;
        this.detailAddress = detailAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageList = imageList;
        this.eventDateList = eventDateList;
    }
}
