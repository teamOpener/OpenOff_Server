package com.example.openoff.domain.ladger.domain.entity;

import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Getter
@Entity
@Table(name = "openoff_event_staff")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventStaff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_staff_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_user_id")
    private User staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_info_id")
    private EventInfo eventInfo;

    @Column(name = "staff_type", nullable = false)
    private StaffType staffType;

    @Column(name = "staff_phone_number")
    @Pattern(regexp = "^010-?\\d{4}-?\\d{4}$", message = "올바른 한국 휴대폰 번호 형식이 아닙니다.")
    private String phoneNumber;

    @Column(name = "staff_email")
    private String email;

    @Column(name = "staff_name")
    private String name;

    @Builder
    public EventStaff(Long id, User staff, EventInfo eventInfo, StaffType staffType, String phoneNumber, String email, String name) {
        this.id = id;
        this.staff = staff;
        this.eventInfo = eventInfo;
        this.staffType = staffType;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.name = name;
    }

    public static EventStaff toEntity(User staff, EventInfo eventInfo, StaffType staffType, String phoneNumber, String email, String name){
        return EventStaff.builder()
                .staff(staff)
                .eventInfo(eventInfo)
                .staffType(staffType)
                .phoneNumber(phoneNumber)
                .email(email)
                .name(name)
                .build();
    }


}
