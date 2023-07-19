package com.example.openoff.domain.eventInstance.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
public class Location {
    //위도, 경도, 도로명주소, 상세주소
    private Double latitude;
    private Double longitude;
    @Column(name = "street_name_address")
    private String streetNameAddress;
    @Column(name = "detail_address")
    private String detailAddress;  // Boolean으로 변경

    @Builder
    public Location(Double latitude, Double longitude, String streetNameAddress, String detailAddress) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.streetNameAddress = streetNameAddress;
        this.detailAddress = detailAddress;
    }

    public static Location of(Double latitude, Double longitude, String streetNameAddress, String detailAddress) {
        return Location.builder()
                .latitude(latitude)
                .longitude(longitude)
                .streetNameAddress(streetNameAddress)
                .detailAddress(detailAddress)
                .build();
    }
}
