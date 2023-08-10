package com.example.openoff.domain.eventInstance.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoAddressResponse {

    private KakaoMeta meta;
    private List<KakaoDocument> documents;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoMeta {
        private int total_count;
        private int pageable_count;
        @JsonProperty("is_end")
        private boolean is_end;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoDocument {
        private String address_name;
        private String y;
        private String x;
        private String address_type;
        private KakaoAddress address;
        private KakaoRoadAddress road_address;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoAddress {
        private String address_name;
        private String region_1depth_name;
        private String region_2depth_name;
        private String region_3depth_name;
        private String region_3depth_h_name;
        private String h_code;
        private String b_code;
        private String mountain_yn;
        private String main_address_no;
        private String sub_address_no;
        private String x;
        private String y;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoRoadAddress {
        private String address_name;
        private String region_1depth_name;
        private String region_2depth_name;
        private String region_3depth_name;
        private String road_name;
        private String underground_yn;
        private String main_building_no;
        private String sub_building_no;
        private String building_name;
        private String zone_no;
        private String y;
        private String x;

    }
}

