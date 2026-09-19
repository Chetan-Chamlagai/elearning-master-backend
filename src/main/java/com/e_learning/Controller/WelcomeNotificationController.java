package com.e_learning.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_learning.payloads.ApiResponse;
import com.e_learning.payloads.WelcomeNotificationRequest;
import com.e_learning.services.WelcomeNotificationService;

@RestController
@RequestMapping("/api/v1/notifications")
public class WelcomeNotificationController {

    private static final Logger logger = LoggerFactory.getLogger(WelcomeNotificationController.class);

    @Autowired
    private WelcomeNotificationService welcomeNotificationService;

    @PostMapping("/welcome/{userId}")
    public ResponseEntity<ApiResponse> sendWelcomeNotification(
            @PathVariable Integer userId,
            @RequestBody(required = false) WelcomeNotificationRequest request) {

        logger.info("Received request to send welcome notification for userId={}", userId);

        String title = request != null ? request.getTitle() : null;
        String message = request != null ? request.getMessage() : null;

        welcomeNotificationService.sendWelcomeNotification(userId, title, message);

        ApiResponse response = new ApiResponse(
                "Welcome event published to Kafka for userId=" + userId, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}