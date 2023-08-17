package com.example.openoff.common.config.batch;

import com.example.openoff.common.infrastructure.fcm.FirebaseService;
import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.eventInstance.domain.repository.EventIndexRepository;
import com.example.openoff.domain.ladger.domain.entity.EventStaff;
import com.example.openoff.domain.notification.domain.entity.Notification;
import com.example.openoff.domain.notification.domain.entity.NotificationType;
import com.example.openoff.domain.notification.domain.repository.NotificationRepository;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@Transactional
@RequiredArgsConstructor
public class EventStaffBatchConfig {
    private final EventIndexRepository eventIndexRepository;
    private final NotificationRepository notificationRepository;
    private final FirebaseService firebaseService;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job eventStaffLeft1DayJob()
    {
        LocalDate now = LocalDate.now();
        return jobBuilderFactory.get("EventLeft1DayStaffStep_"+now)
                .preventRestart()
                .start(EventStaffLeft1DayStep())
                .build();
    }

    @Bean
    public Job eventStaffLeftDDayJob()
    {
        LocalDate now = LocalDate.now();
        return jobBuilderFactory.get("EventLeftDDayStaffStep_"+now)
                .preventRestart()
                .start(EventStaffLeftDDayStep())
                .build();
    }

    @Bean
    public Step EventStaffLeft1DayStep()
    {
        LocalDate now = LocalDate.now();
        return stepBuilderFactory.get("EventLeft1DayStaffStep_"+now)
                .<EventIndex, List<Notification>> chunk(10)
                .reader(eventStaff1DayLeftReader())
                .processor(eventStaff1DayLeftNotificationProcessor())
                .writer(staffNotificationWriter())
                .build();
    }

    @Bean
    public Step EventStaffLeftDDayStep()
    {
        LocalDate now = LocalDate.now();
        return stepBuilderFactory.get("EventLeftDDayStaffStep_"+now)
                .<EventIndex, List<Notification>> chunk(10)
                .reader(eventStaffDDayLeftReader())
                .processor(eventStaffDDayLeftNotificationProcessor())
                .writer(staffNotificationWriter())
                .build();
    }

    @Bean
    @StepScope
    public ListItemReader<EventIndex> eventStaff1DayLeftReader() {
        List<EventIndex> eventIndexList = eventIndexRepository.find1DayLeftEventIndex();
        return new ListItemReader<>(eventIndexList);
    }

    @Bean
    @StepScope
    public ListItemReader<EventIndex> eventStaffDDayLeftReader() {
        List<EventIndex> eventIndexList = eventIndexRepository.findDDayLeftEventIndex();
        return new ListItemReader<>(eventIndexList);
    }

    public ItemProcessor<EventIndex, List<Notification>> eventStaff1DayLeftNotificationProcessor() {
        return new ItemProcessor<EventIndex, List<Notification>>() {
            @Override
            public List<Notification> process(EventIndex item) throws Exception {
                String eventTitle = item.getEventInfo().getEventTitle();
                List<User> staffs = item.getEventInfo().getEventStaffs().stream().map(EventStaff::getStaff).collect(Collectors.toList());
                firebaseService.sendToTopic(item.getId()+"-1day-staff-alert", "["+eventTitle+"] 이벤트 하루 전이에요!", "이벤트 날짜가 하루 전으로 다가왔어요! ");
                return staffs.stream().map(user -> Notification.builder()
                        .user(user)
                        .content("이벤트 날짜가 하루 전으로 다가왔어요!")
                        .isRead(false)
                        .notificationType(NotificationType.E)
                        .notificationParameter(item.getId()).build())
                        .collect(Collectors.toList());
            }
        };
    }

    public ItemProcessor<EventIndex, List<Notification>> eventStaffDDayLeftNotificationProcessor() {
        return new ItemProcessor<EventIndex, List<Notification>>() {
            @Override
            public List<Notification> process(EventIndex item) throws Exception {
                String eventTitle = item.getEventInfo().getEventTitle();
                List<User> staffs = item.getEventInfo().getEventStaffs().stream().map(EventStaff::getStaff).collect(Collectors.toList());
                firebaseService.sendToTopic(item.getId()+"-dday-staff-alert", "["+eventTitle+"] 이벤트 당일이에요!", "이벤트 당일이 되었습니다!\n참석 명단을 다시 한 번 체크해주세요.");
                return staffs.stream().map(user -> Notification.builder()
                                .user(user)
                                .content("이벤트 당일이 되었습니다!\n참석 명단을 다시 한 번 체크해주세요.")
                                .isRead(false)
                                .notificationType(NotificationType.E)
                                .notificationParameter(item.getId()).build())
                        .collect(Collectors.toList());
            }
        };
    }

    public ItemWriter<List<Notification>> staffNotificationWriter() {
        return list -> {
            List<Notification> flattenNotifications = list.stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
            notificationRepository.saveAll(flattenNotifications);
        };
    }

}
