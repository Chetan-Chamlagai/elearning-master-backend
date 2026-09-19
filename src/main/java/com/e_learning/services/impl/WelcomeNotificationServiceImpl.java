package com.e_learning.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.e_learning.entities.User;
import com.e_learning.exceptions.ResourceNotFoundException;
import com.e_learning.kafka.NotificationEventProducer;
import com.e_learning.repositories.UserRepo;
import com.e_learning.services.WelcomeNotificationService;

@Service
public class WelcomeNotificationServiceImpl implements WelcomeNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(WelcomeNotificationServiceImpl.class);
    private static final String DEFAULT_TITLE = "Welcome to E-Learning Platform!";

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private NotificationEventProducer notificationEventProducer;

    @Override
    public void sendWelcomeNotification(Integer userId) {
        sendWelcomeNotification(userId, null, null);
    }

    @Override
    public void sendWelcomeNotification(Integer userId, String title, String message) {

        // Step 1: user exist xa ki check garne — invalid userId lai
        // Kafka samma pugna nadine, database level ma nai catch garne
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", (long) userId));

        // Step 2: default value fallback
        String finalTitle = StringUtils.hasText(title) ? title : DEFAULT_TITLE;
        String finalMessage = StringUtils.hasText(message)
                ? message
                : buildDefaultWelcomeMessage(user.getName());

        logger.info("Publishing welcome event for userId={}", userId);

        // Step 3: Kafka ma publish — email pathaudaina, userId matra
        notificationEventProducer.publishEmail(userId, finalTitle, finalMessage);
    }

    private String buildDefaultWelcomeMessage(String userName) {
        String name = StringUtils.hasText(userName) ? userName : "there";
        return "Hi " + name + ", welcome to the E-Learning Platform! "
                + "We're excited to have you on board.";
    }
}