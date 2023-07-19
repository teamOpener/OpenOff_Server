package com.example.openoff.domain.bookmark.domain.entity;

import com.example.openoff.common.infrastructure.domain.BaseEntity;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "openoff_event_bookmark")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventBookmark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_info_id")
    private EventInfo eventInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public EventBookmark(EventInfo eventInfo, User user) {
        this.eventInfo = eventInfo;
        this.user = user;
    }

    public static EventBookmark toEntity(EventInfo eventInfo, User user) {
        return EventBookmark.builder()
                .eventInfo(eventInfo)
                .user(user)
                .build();
    }
}
