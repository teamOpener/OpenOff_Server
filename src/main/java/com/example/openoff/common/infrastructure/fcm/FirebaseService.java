package com.example.openoff.common.infrastructure.fcm;

import com.example.openoff.domain.user.domain.entity.User;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FirebaseService {
    private final FirebaseMessaging firebaseMessaging;

    // Android 세팅
    private AndroidConfig androidConfig(String title, String body) {
        return AndroidConfig.builder()
                .setNotification(AndroidNotification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();
    }

    // APNs 세팅 ( iOS )
    private ApnsConfig apnsConfig(String title, String body) {
        return ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setAlert(
                                ApsAlert.builder()
                                        .setTitle(title)
                                        .setBody(body)
                                        .build()
                        )
                        .setSound("default")
                        .build())
                .build();
    }

    public void sendFCMNotificationSingle(User user, String title, String body) {
        if (user.getFcmToken() == null) return;
        Notification notification = Notification.builder()
                .setTitle(title).setBody(body).build();

        Message message = Message.builder()
                .setAndroidConfig(androidConfig(title, body))
                .setApnsConfig(apnsConfig(title, body))
                .setNotification(notification)
                .setToken(user.getFcmToken())
                .build();
        try {
            firebaseMessaging.send(message);
        } catch (Exception e) {
            return;
        }
    }

    public void sendFCMNotificationMulticast(List<User> users, String title, String body) {
        Notification notification = Notification.builder()
                .setTitle(title).setBody(body).build();
        MulticastMessage multicastMessage = MulticastMessage.builder()
                .setNotification(notification)
                .setAndroidConfig(androidConfig(title, body))
                .setApnsConfig(apnsConfig(title, body))
                .addAllTokens(users.stream()
                        .map(User::getFcmToken)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()))
                .build();

        firebaseMessaging.sendMulticastAsync(multicastMessage);
    }

    public void sendToTopic(String topic, String title, String body) {
        Notification notification = Notification.builder()
                .setTitle(title).setBody(body).build();

        Message message = Message.builder()
                .setAndroidConfig(androidConfig(title, body))
                .setApnsConfig(apnsConfig(title, body))
                .setNotification(notification)
                .setTopic(topic)
                .build();

        firebaseMessaging.sendAsync(message);
    }

    public void subScribe(String topicName, List<String> fcmTokenList) {
        firebaseMessaging.subscribeToTopicAsync(
                fcmTokenList,
                topicName
        );
    }

    public void unSubscribe(String topicName, List<String> fcmTokenList) {
        firebaseMessaging.unsubscribeFromTopicAsync(
                fcmTokenList,
                topicName
        );
    }
}
