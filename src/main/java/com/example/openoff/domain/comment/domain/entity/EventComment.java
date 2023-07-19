package com.example.openoff.domain.comment.domain.entity;

import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "openoff_event_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_info_id")
    private EventInfo eventInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private EventComment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventComment> children = new ArrayList<>();

    @Builder
    public EventComment(String content) {
        this.content = content;
    }

    public static EventComment toEntity(String content) {
        return EventComment.builder()
                .content(content)
                .build();
    }

    public void updateEventInfo(EventInfo eventInfo) {
        this.eventInfo = eventInfo;
    }

    public void updateWriter(User writer) {
        this.writer = writer;
    }

    public void updateParent(EventComment parent) {
        this.parent = parent;
    }
}
