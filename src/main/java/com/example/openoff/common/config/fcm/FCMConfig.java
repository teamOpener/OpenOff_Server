package com.example.openoff.common.config.fcm;

import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class FCMConfig {
    @Bean
    FirebaseMessaging firebaseMessaging() {
        try {
            ClassPathResource resource = new ClassPathResource("firebase/openoff-484dd-firebase-adminsdk-ruh1l-e458eb4d19.json");

            InputStream refreshToken = resource.getInputStream();

            FirebaseApp firebaseApp = null;
            List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();

            if (firebaseAppList != null && !firebaseAppList.isEmpty()) {
                for (FirebaseApp app : firebaseAppList) {
                    if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
                        firebaseApp = app;
                    }
                }
            } else {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(refreshToken))
                        .build();

                firebaseApp = FirebaseApp.initializeApp(options);
            }
            return FirebaseMessaging.getInstance(firebaseApp);
        } catch (IOException e) {
            throw BusinessException.of(Error.INTERNAL_SERVER_ERROR);
        }
    }
}
