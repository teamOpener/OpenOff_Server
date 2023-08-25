package com.example.openoff.common.config.batch;

import com.example.openoff.common.infrastructure.fcm.FirebaseService;
import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.repository.EventIndexRepository;
import com.example.openoff.domain.ladger.domain.repository.EventApplicantLadgerRepository;
import com.example.openoff.domain.notification.domain.entity.Notification;
import com.example.openoff.domain.notification.domain.entity.NotificationType;
import com.example.openoff.domain.notification.domain.repository.NotificationRepository;
import com.example.openoff.domain.user.domain.entity.User;
import com.example.openoff.domain.user.domain.repository.UserFcmTokenRepository;
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
public class EventApplicantBatchConfig {
    private final EventIndexRepository eventIndexRepository;
    private final EventApplicantLadgerRepository eventApplicantLadgerRepository;
    private final NotificationRepository notificationRepository;
    private final UserFcmTokenRepository userFcmTokenRepository;
    private final FirebaseService firebaseService;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job eventApplicantLeft1DayJob()
    {
        LocalDate now = LocalDate.now();
        return jobBuilderFactory.get("EventLeft1DayApplicantStep_"+now)
                .preventRestart()
                .start(EventApplicantLeft1DayStep())
                .build();
    }

    @Bean
    public Job eventApplicantLeftDDayJob()
    {
        LocalDate now = LocalDate.now();
        return jobBuilderFactory.get("EventLeftDDayApplicantStep_"+now)
                .preventRestart()
                .start(EventApplicantLeftDDayStep())
                .build();
    }

    @Bean
    public Step EventApplicantLeft1DayStep()
    {
        LocalDate now = LocalDate.now();
        return stepBuilderFactory.get("EventLeft1DayApplicantStep_"+now)
                .<EventIndex, List<Notification>> chunk(10)
                .reader(eventApplicant1DayLeftReader())
                .processor(eventApplicant1DayLeftNotificationProcessor())
                .writer(applicantNotificationWriter())
                .build();
    }

    @Bean
    public Step EventApplicantLeftDDayStep()
    {
        LocalDate now = LocalDate.now();
        return stepBuilderFactory.get("EventLeftDDayApplicantStep_"+now)
                .<EventIndex, List<Notification>> chunk(10)
                .reader(eventApplicantDDayLeftReader())
                .processor(eventApplicantDDayLeftNotificationProcessor())
                .writer(applicantNotificationWriter())
                .build();
    }

    @Bean
    @StepScope
    public ListItemReader<EventIndex> eventApplicant1DayLeftReader() {
        List<EventIndex> eventIndexList = eventIndexRepository.find1DayLeftEventIndex();
        return new ListItemReader<>(eventIndexList);
    }

    @Bean
    @StepScope
    public ListItemReader<EventIndex> eventApplicantDDayLeftReader() {
        List<EventIndex> eventIndexList = eventIndexRepository.findDDayLeftEventIndex();
        return new ListItemReader<>(eventIndexList);
    }

    public ItemProcessor<EventIndex, List<Notification>> eventApplicant1DayLeftNotificationProcessor() {
        return new ItemProcessor<EventIndex, List<Notification>>() {
            @Override
            public List<Notification> process(EventIndex item) throws Exception {
                EventInfo eventInfo = item.getEventInfo();
                List<User> acceptedApplicantInEventIndex = eventApplicantLadgerRepository.findAcceptedApplicantInEventIndex(item.getId());
                List<String> userIds = acceptedApplicantInEventIndex.stream().map(User::getId).collect(Collectors.toList());
                List<String> allFcmTokens = userFcmTokenRepository.findAllFcmTokens(userIds);
                firebaseService.sendToTopic(eventInfo.getId()+"-1day-applicant-alert", "["+eventInfo.getEventTitle()+"] 이벤트 하루 전이에요!", "이벤트 날짜가 하루 전으로 다가왔어요!");
                if (!allFcmTokens.isEmpty())
                    firebaseService.unSubscribe(eventInfo.getId()+"-1day-applicant-alert", allFcmTokens);
                return acceptedApplicantInEventIndex.stream().map(user -> Notification.builder()
                        .user(user)
                        .content("이벤트 날짜가 하루 전으로 다가왔어요!")
                        .isRead(false)
                        .notificationType(NotificationType.E)
                        .notificationParameter(eventInfo.getId()).build())
                        .collect(Collectors.toList());
            }
        };
    }

    public ItemProcessor<EventIndex, List<Notification>> eventApplicantDDayLeftNotificationProcessor() {
        return new ItemProcessor<EventIndex, List<Notification>>() {
            @Override
            public List<Notification> process(EventIndex item) throws Exception {
                EventInfo eventInfo = item.getEventInfo();
                List<User> acceptedApplicantInEventIndex = eventApplicantLadgerRepository.findAcceptedApplicantInEventIndex(item.getId());
                List<String> userIds = acceptedApplicantInEventIndex.stream().map(User::getId).collect(Collectors.toList());
                List<String> allFcmTokens = userFcmTokenRepository.findAllFcmTokens(userIds);
                firebaseService.sendToTopic(eventInfo.getId()+"-dday-applicant-alert", "["+eventInfo.getEventTitle()+"] 이벤트 날이에요!", "이벤트 참여 당일입니다!!");
                if (!allFcmTokens.isEmpty())
                    firebaseService.unSubscribe(eventInfo.getId()+"-dday-applicant-alert", allFcmTokens);
                return acceptedApplicantInEventIndex.stream().map(user -> Notification.builder()
                                .user(user)
                                .content("이벤트 참여 당일입니다!!")
                                .isRead(false)
                                .notificationType(NotificationType.E)
                                .notificationParameter(eventInfo.getId()).build())
                        .collect(Collectors.toList());
            }
        };
    }

    public ItemWriter<List<Notification>> applicantNotificationWriter() {
        return list -> {
            List<Notification> flattenNotifications = list.stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
            notificationRepository.saveAll(flattenNotifications);
        };
    }

}
