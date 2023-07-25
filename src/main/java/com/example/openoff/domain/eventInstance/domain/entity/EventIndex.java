package com.example.openoff.domain.eventInstance.domain.entity;

import com.example.openoff.common.infrastructure.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "openoff_event_index")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventIndex extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_index_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_info_id")
    private EventInfo eventInfo;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Column(name = "is_close", nullable = false)
    private Boolean isClose;

    @Builder
    public EventIndex(EventInfo eventInfo, LocalDateTime eventDate, Boolean isClose) {
        this.eventInfo = eventInfo;
        this.eventDate = eventDate;
        this.isClose = isClose;
    }

    public static EventIndex toEntity(EventInfo eventInfo, LocalDateTime eventDate){
        return EventIndex.builder()
                .eventInfo(eventInfo)
                .eventDate(eventDate)
                .isClose(false)
                .build();
    }

    public void updateEventIndexIsClose(Boolean isClose){
        this.isClose = isClose;
    }
}