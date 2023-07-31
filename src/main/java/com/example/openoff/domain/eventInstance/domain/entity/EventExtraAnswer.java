package com.example.openoff.domain.eventInstance.domain.entity;

import com.example.openoff.common.infrastructure.domain.BaseEntity;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "openoff_event_extra_answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventExtraAnswer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_extra_answer_id")
    private Long id;

    @Column(name = "answer", nullable = false)
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answerer_id")
    private User answerer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_index_id")
    private EventIndex eventIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_extra_question_id")
    private EventExtraQuestion question;

    @Builder
    public EventExtraAnswer(String answer, User answerer, EventIndex eventIndex, EventExtraQuestion question) {
        this.answer = answer;
        this.answerer = answerer;
        this.eventIndex = eventIndex;
        this.question = question;
    }

    public static EventExtraAnswer toEntity(String answer, User answerer, EventIndex eventIndex, EventExtraQuestion question){
        return EventExtraAnswer.builder()
                .answer(answer)
                .answerer(answerer)
                .eventIndex(eventIndex)
                .question(question)
                .build();
    }
}
