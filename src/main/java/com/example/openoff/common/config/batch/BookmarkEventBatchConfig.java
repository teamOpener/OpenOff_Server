package com.example.openoff.common.config.batch;

import com.example.openoff.common.infrastructure.fcm.FirebaseService;
import com.example.openoff.domain.bookmark.domain.entity.EventBookmark;
import com.example.openoff.domain.bookmark.domain.repository.EventBookmarkRepository;
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
import java.util.List;

@Slf4j
@Configuration
@Transactional
@RequiredArgsConstructor
public class BookmarkEventBatchConfig {
    private final EventBookmarkRepository eventBookmarkRepository;
    private final NotificationRepository notificationRepository;
    private final FirebaseService firebaseService;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job bookmarkEventJob()
    {
        LocalDate now = LocalDate.now();
        return jobBuilderFactory.get("bookmarkEventApplyLeft1DayJob_"+now)
                .preventRestart()
                .start(bookmarkEventApplyLeft1DayStep())
                .build();
    }

    @Bean
    public Step bookmarkEventApplyLeft1DayStep()
    {
        LocalDate now = LocalDate.now();
        return stepBuilderFactory.get("bookmarkEventApplyLeft1DayStep_"+now)
                .<EventBookmark, Notification> chunk(10)
                .reader(eventBookmark1DayLeftReader())
                .processor(eventBookmark1DayLeftNotificationProcessor())
                .writer(eventBookmark1DayLeftNotificationWriter())
                .build();
    }

    @Bean
    @StepScope
    public ListItemReader<EventBookmark> eventBookmark1DayLeftReader() {
        List<EventBookmark> eventBookmarks = eventBookmarkRepository.findApply1DayLeftEventBookmark();
        return new ListItemReader<>(eventBookmarks);
    }

    public ItemProcessor<EventBookmark, Notification> eventBookmark1DayLeftNotificationProcessor() {
        return new ItemProcessor<EventBookmark, Notification>() {
            @Override
            public Notification process(EventBookmark item) throws Exception {
                firebaseService.sendToTopic(item.getId()+"-bookmark-1day", "이벤트 마감이 얼마 남지 않았어요!", "이벤트 신청 마감 1일 전입니다.");
                User user = item.getUser();
                return Notification.builder()
                        .user(user)
                        .content("이벤트 신청 마감 1일 전입니다.")
                        .isRead(false)
                        .notificationType(NotificationType.E)
                        .notificationParameter(item.getEventInfo().getId()).build();
            }
        };
    }

    public ItemWriter<Notification> eventBookmark1DayLeftNotificationWriter() {
        return (notificationRepository::saveAll);
    }

}
