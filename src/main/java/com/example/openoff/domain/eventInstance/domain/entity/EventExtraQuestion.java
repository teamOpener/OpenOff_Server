package com.example.openoff.domain.eventInstance.domain.entity;

import com.example.openoff.common.infrastructure.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "openoff_event_extra_question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventExtraQuestion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_extra_question_id")
    private Long id;

    @Column(name = "question", nullable = false)
    private String question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_info_id")
    private EventInfo eventInfo;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<EventExtraAnswer> eventExtraAnswerList;

    @Builder
    public EventExtraQuestion(String question, EventInfo eventInfo) {
        this.question = question;
        this.eventInfo = eventInfo;
    }

    public static EventExtraQuestion toEntity(String question, EventInfo eventInfo){
        return EventExtraQuestion.builder()
                .question(question)
                .eventInfo(eventInfo)
                .build();
    }
}
