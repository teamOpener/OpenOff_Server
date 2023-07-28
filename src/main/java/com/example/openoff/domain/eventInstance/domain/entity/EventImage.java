package com.example.openoff.domain.eventInstance.domain.entity;

import com.example.openoff.common.infrastructure.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

@Getter
@Entity
@BatchSize(size = 5)
@Table(name = "openoff_event_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_image_id")
    private Long id;

    @Column(name = "event_image_url", nullable = false)
    private String eventImageUrl;

    @Column(name = "is_main")
    private Boolean isMain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_info_id")
    private EventInfo eventInfo;

    @Builder
    public EventImage(Long id, String eventImageUrl, Boolean isMain, EventInfo eventInfo) {
        this.id = id;
        this.eventImageUrl = eventImageUrl;
        this.isMain = isMain;
        this.eventInfo = eventInfo;
    }

    public static EventImage toEntity(String eventImageUrl, Boolean isMain, EventInfo eventInfo){
        return EventImage.builder()
                .eventImageUrl(eventImageUrl)
                .isMain(isMain)
                .eventInfo(eventInfo)
                .build();
    }
}
