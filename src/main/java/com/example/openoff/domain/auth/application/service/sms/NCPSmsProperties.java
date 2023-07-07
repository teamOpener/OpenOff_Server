package com.example.openoff.domain.auth.application.service.sms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "ncp.sms")
public class NCPSmsProperties {
    private final String accessKey;

    private final String secretKey;

    private final String serviceId;

    private final String senderPhone;

}
