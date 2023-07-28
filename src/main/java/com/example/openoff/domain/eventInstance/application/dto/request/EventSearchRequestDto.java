package com.example.openoff.domain.eventInstance.application.dto.request;

import com.example.openoff.domain.eventInstance.presentation.CapacityRange;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import com.google.firebase.database.annotations.Nullable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventSearchRequestDto {
    private Double latitude;
    private Double longitude;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @Nullable
    private String keyword;

    @Nullable
    private FieldType field;

    @Nullable
    private Integer eventFee;

    @Nullable
    private CapacityRange capacity;

    @Nullable
    private Boolean applyable;

    @Builder
    public EventSearchRequestDto(Double latitude, Double longitude, LocalDateTime startDate, LocalDateTime endDate, String keyword, FieldType field, Integer eventFee, CapacityRange capacity, Boolean applyable) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.startDate = startDate;
        this.endDate = endDate;
        this.keyword = keyword;
        this.field = field;
        this.eventFee = eventFee;
        this.capacity = capacity;
        this.applyable = applyable;
    }
}
