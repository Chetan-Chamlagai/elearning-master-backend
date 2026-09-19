package com.e_learning.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Publishes to the SAME topic name the notification-system consumer
 * listens on: "simple-notification-events".
 *
 * See: com.notify.service.NotificationConsumer in notification-system-simple.
 */
@Service
public class NotificationEventProducer {

    private static final String TOPIC = "simple-notification-events";

    @Autowired
    private KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    /**
     * @param userId  matches the userId in the notification-system's own
     *                "user" table (it has its own DB - see note below).
     * @param title   email subject.
     * @param message email body.
     * @param channel "email" for now (phase 1).
     */
    public void publish(Integer userId, String title, String message, String channel) {
        NotificationEvent event = new NotificationEvent(userId, title, message, channel);
        System.out.println("E_LEARNING PRODUCER: publishing -> " + event);
        // key = userId -> keeps all events for the same user in order,
        // in the same Kafka partition (matches consumer side convention).
        kafkaTemplate.send(TOPIC, String.valueOf(userId), event);
    }

    public void publishEmail(Integer userId, String title, String message) {
        publish(userId, title, message, "email");
    }
}
