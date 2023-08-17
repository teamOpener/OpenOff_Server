package com.example.openoff;

import com.example.openoff.common.security.jwt.JwtProperties;
import com.example.openoff.domain.auth.application.service.google.GoogleOAuthProperties;
import com.example.openoff.domain.auth.application.service.sms.NCPSmsProperties;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = "com.example.openoff")
@EnableJpaAuditing
@EnableConfigurationProperties({JwtProperties.class, GoogleOAuthProperties.class, NCPSmsProperties.class})
@EnableScheduling
@EnableBatchProcessing
public class OpenoffApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenoffApplication.class, args);
    }

}
