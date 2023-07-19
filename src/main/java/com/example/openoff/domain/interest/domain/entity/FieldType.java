package com.example.openoff.domain.interest.domain.entity;

import com.example.openoff.common.infrastructure.domain.CodeValue;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum FieldType implements CodeValue {
    LCS("Lecture/Seminar/Conference", "강연/세미나/컨퍼런스"), // Lecture/Seminar/Conference
    EE("Exhibition/Expo", "전시/박람회"), // Exhibition/Expo
    S("Show", "공연"), // Show
    EA("Exercise/Activity", "운동/액티비티"), // Exercise/Activity
    FD("Food/Drink", "푸드/드링크"), // Food/Drink
    PF("Party/Festival","파티/페스티벌"), // Party/Festival
    FSDH("FellowShip/DailyHope","친목/일일호프"); // Fellowship/Daily Hope
    private final String code;
    private final String value;

    FieldType(String code, String value) {
        this.code = code;
        this.value = value;
    }
    @Override
    public String getCode() {
        return code;
    }
    @Override
    public String getValue() {
        return value;
    }

    @Getter
    public static class InterestTypeInfo{
        private final String constName;
        private final String code;
        private final String value;

        @Builder
        public InterestTypeInfo(String constName, String code, String value) {
            this.constName = constName;
            this.code = code;
            this.value = value;
        }

    }

    public static List<InterestTypeInfo> getAllInterestTypeInfo() {
        return Arrays.stream(FieldType.values())
                .map(e -> InterestTypeInfo.builder().constName(e.name()).code(e.getCode()).value(e.getValue()).build())
                .collect(Collectors.toList());
    }

}
