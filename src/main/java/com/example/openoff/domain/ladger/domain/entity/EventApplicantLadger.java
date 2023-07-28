package com.example.openoff.domain.ladger.domain.entity;

import com.example.openoff.common.infrastructure.domain.BaseEntity;
import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@BatchSize(size = 30)
@Table(name = "openoff_event_applicant_ladger",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_event_applicant_ladger_index_id_applicant_id",
                        columnNames = {"event_index_id","event_applicant_id"}
                )
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventApplicantLadger extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_applicant_ladger_id")
    private Long id;

    @Column(name = "ticket_index", columnDefinition = "VARCHAR(20)", unique = true, updatable = false)
    private String ticketIndex;

    @Column(name = "ticket_type")
    private TicketType ticketType;

    @Column(name = "qr_code_image_url")
    private String qrCodeImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_index_id")
    private EventIndex eventIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_applicant_id")
    private User eventApplicant;

    @Column(name = "is_accept", nullable = false)
    private Boolean isAccept;

    @Column(name = "is_join", nullable = false)
    private Boolean isJoin;

    @Column(name = "join_at")
    private LocalDateTime joinAt;

    @Builder
    public EventApplicantLadger(Long id, String ticketIndex, TicketType ticketType, EventIndex eventIndex, User eventApplicant, Boolean isAccept, Boolean isJoin, LocalDateTime joinAt, String qrCodeImageUrl) {
        this.id = id;
        this.ticketIndex = ticketIndex;
        this.ticketType = ticketType;
        this.eventIndex = eventIndex;
        this.eventApplicant = eventApplicant;
        this.isAccept = isAccept;
        this.isJoin = isJoin;
        this.joinAt = joinAt;
        this.qrCodeImageUrl = qrCodeImageUrl;
    }

    public static EventApplicantLadger toEntity(EventIndex eventIndex, User eventApplicant){
        return EventApplicantLadger.builder()
                .ticketIndex("E-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase())
                .ticketType(TicketType.getRandomTicketType())
                .eventIndex(eventIndex)
                .eventApplicant(eventApplicant)
                .isAccept(false)
                .isJoin(false)
                .build();
    }

    public void updateTicketIndex(String ticketIndex) {
        this.ticketIndex = ticketIndex;
    }

    public void updateIsAcceptAndQrCodeUrl(Boolean isAccept, String qrCodeImageUrl) {
    	this.isAccept = isAccept;
        this.qrCodeImageUrl = qrCodeImageUrl;
    }

    public void updateIsJoinAndJoinAt(Boolean isJoin) {
    	this.isJoin = isJoin;
        this.joinAt = LocalDateTime.now();
    }
}
