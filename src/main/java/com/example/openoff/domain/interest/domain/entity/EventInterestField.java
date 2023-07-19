package com.example.openoff.domain.interest.domain.entity;

import com.example.openoff.common.infrastructure.domain.BaseEntity;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "openoff_event_interest_field",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_event_info_field_type",
                        columnNames = {"event_info_id","field_type"}
                )
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventInterestField extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_field_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_info_id", nullable = false)
    private EventInfo eventInfo;

    @Column(name = "field_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FieldType fieldType;

    @Builder
    public EventInterestField(EventInfo eventInfo, FieldType fieldType) {
        this.eventInfo = eventInfo;
        this.fieldType = fieldType;
    }

    public static EventInterestField toEntity(EventInfo eventInfo, FieldType fieldType){
        return EventInterestField.builder()
                .eventInfo(eventInfo)
                .fieldType(fieldType)
                .build();
    }
}
