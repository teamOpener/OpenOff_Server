package com.example.openoff.common.config.batch;

import com.example.openoff.common.config.web.AsyncConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchScheduler {
    private final JobLauncher jobLauncher;
    private final BookmarkEventBatchConfig bookmarkEventBatchConfig;
    private final EventStaffBatchConfig eventStaffBatchConfig;
    private final AsyncConfig asyncConfig;



    @Scheduled(cron = "0 0 9 * * *")
    public void bookmarkEventApplyLeft1DayJob() {
        Map<String, JobParameter> confMap = Map.of("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);

        try{
            jobLauncher.run(bookmarkEventBatchConfig.bookmarkEventJob(), jobParameters);
        } catch (Exception e) {
            log.error("bookmarkEventApplyLeft1DayJob error : {}", e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 7 * * *")
    public void eventStaffJob() {
        Map<String, JobParameter> confMap = Map.of("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);

        try {
            asyncConfig.taskExecutor().execute(() -> {
                try {
                    jobLauncher.run(eventStaffBatchConfig.eventLeft1DayJob(), jobParameters);
                } catch (Exception e) {
                    log.error("Error running eventLeft1DayJob : {}", e.getMessage());
                }
            });

            asyncConfig.taskExecutor().execute(() -> {
                try {
                    jobLauncher.run(eventStaffBatchConfig.eventLeftDDayJob(), jobParameters);
                } catch (Exception e) {
                    log.error("Error running eventLeftDDayJob : {}", e.getMessage());
                }
            });
        } catch (Exception e) {
            log.error("eventStaffJob error : {}", e.getMessage());
        }
    }
}
