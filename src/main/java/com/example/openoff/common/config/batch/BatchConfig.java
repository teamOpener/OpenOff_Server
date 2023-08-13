//package com.example.openoff.common.config.batch;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Slf4j
//@Configuration
//@EnableBatchProcessing
//public class BatchConfig {
//
//    @Autowired
//    public JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    public StepBuilderFactory stepBuilderFactory;
//
//    @Autowired
//    private DocumentRepository documentRepository;
//
//    @Bean
//    public Job job() {
//
//        Job job = jobBuilderFactory.get("job")
//                .start(step())
//                .build();
//
//        return job;
//    }
//
//    @Bean
//    public Step step() {
//        return stepBuilderFactory.get("step")
//                .tasklet((contribution, chunkContext) -> {
//                    log.info("Step!");
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }
//}
