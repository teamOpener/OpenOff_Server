package com.example.openoff.domain.eventInstance.domain.entity;

import com.example.openoff.common.infrastructure.domain.BaseEntity;
import com.example.openoff.domain.bookmark.domain.entity.EventBookmark;
import com.example.openoff.domain.interest.domain.entity.EventInterestField;
import com.example.openoff.domain.ladger.domain.entity.EventStaff;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@BatchSize(size = 100)
@Table(name = "openoff_event_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_info_id")
    private Long id;

    @Column(name = "event_title", nullable = false)
    private String eventTitle;

    @Column(name = "event_fee", nullable = false)
    private Integer eventFee;

    @Column(name = "event_max_people")
    private Integer eventMaxPeople;

    @Column(name = "total_register_count")
    private Integer totalRegisterCount;

    @Column(name = "event_description")
    private String eventDescription;

    @Column(name = "event_apply_permit")
    private Boolean eventApplyPermit;

    @Column(name = "event_apply_start_date", nullable = false)
    private LocalDateTime eventApplyStartDate;

    @Column(name = "event_apply_end_date", nullable = false)
    private LocalDateTime eventApplyEndDate;

    @Column(name = "is_approval", nullable = false)
    private Boolean isApproval;

    @Embedded
    private Location location;

    @OneToMany(mappedBy = "eventInfo", cascade = CascadeType.ALL)
    private List<EventIndex> eventIndexes;

    @OneToMany(mappedBy = "eventInfo", cascade = CascadeType.ALL)
    private List<EventImage> eventImages;

    @OneToMany(mappedBy = "eventInfo", cascade = CascadeType.ALL)
    private List<EventInterestField> eventInterestFields;

    @OneToMany(mappedBy = "eventInfo", cascade = CascadeType.ALL)
    private List<EventStaff> eventStaffs;

    @OneToMany(mappedBy = "eventInfo", cascade = CascadeType.ALL)
    private List<EventBookmark> eventBookmarks;

    @OneToMany(mappedBy = "eventInfo", cascade = CascadeType.ALL)
    private List<EventExtraQuestion> eventExtraQuestions;

    @Builder
    public EventInfo(String eventTitle, Integer eventFee, Integer eventMaxPeople, Integer totalRegisterCount, String eventDescription,
                     Boolean eventApplyPermit, LocalDateTime eventApplyStartDate, LocalDateTime eventApplyEndDate, Boolean isApproval,
                     Double latitude, Double longitude, String streetNameAddress, String detailAddress) {
        this.eventTitle = eventTitle;
        this.eventFee = eventFee;
        this.eventMaxPeople = eventMaxPeople;
        this.totalRegisterCount = totalRegisterCount;
        this.eventDescription = eventDescription;
        this.eventApplyPermit = eventApplyPermit;
        this.eventApplyStartDate = eventApplyStartDate;
        this.eventApplyEndDate = eventApplyEndDate;
        this.isApproval = isApproval;
        this.location = Location.of(latitude, longitude, streetNameAddress, detailAddress);
    }

    public static EventInfo toEntity(String eventTitle, Integer eventFee, Integer eventMaxPeople, String eventDescription,
                                     LocalDateTime eventApplyStartDate, LocalDateTime eventApplyEndDate,
                                     Double latitude, Double longitude, String streetNameAddress, String detailAddress) {
        return EventInfo.builder()
                .eventTitle(eventTitle)
                .eventFee(eventFee)
                .eventMaxPeople(eventMaxPeople)
                .totalRegisterCount(0)
                .eventDescription(eventDescription)
                .eventApplyPermit(true)
                .eventApplyStartDate(eventApplyStartDate)
                .eventApplyEndDate(eventApplyEndDate)
                .isApproval(false)
                .latitude(latitude).longitude(longitude).streetNameAddress(streetNameAddress).detailAddress(detailAddress)
                .build();
    }

    public void updateIsApproval(Boolean isApproval) {
        this.isApproval = isApproval;
    }

    public void updateApplyPermit(Boolean eventApplyPermit) {
        this.eventApplyPermit = eventApplyPermit;
    }

    public void updateTotalRegisterCount() {
        this.totalRegisterCount += 1;
    }
}
