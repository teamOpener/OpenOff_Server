package com.example.openoff.domain.ladger.application.dto.response;

import com.example.openoff.domain.ladger.domain.entity.TicketType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MyTicketInfoResponseDto {
    private String username;
    private String birth;
    private Long eventInfoId;
    private Long eventIndexId;
    private String eventTitle;
    private String streetRoadAddress;
    private String ticketIndex;
    private TicketType ticketType;
    private LocalDateTime eventDate;
    private Boolean isAccepted;
    private Boolean isJoined;
    private String qrImageUrl;

    @Builder
    public MyTicketInfoResponseDto(String username, String birth, Long eventInfoId, Long eventIndexId, String eventTitle,
                                   String streetRoadAddress, String ticketIndex, TicketType ticketType,
                                   LocalDateTime eventDate, Boolean isAccepted, Boolean isJoined, String qrImageUrl) {
        this.username = username;
        this.birth = birth;
        this.eventInfoId = eventInfoId;
        this.eventIndexId = eventIndexId;
        this.eventTitle = eventTitle;
        this.streetRoadAddress = streetRoadAddress;
        this.ticketIndex = ticketIndex;
        this.ticketType = ticketType;
        this.eventDate = eventDate;
        this.isAccepted = isAccepted;
        this.isJoined = isJoined;
        this.qrImageUrl = qrImageUrl;
    }
}
