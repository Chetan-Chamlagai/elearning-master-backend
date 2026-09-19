package com.e_learning.kafka;

/**
 * Event payload sent to the notification-system's Kafka topic
 * "simple-notification-events".
 *
 * Field names MUST match com.notify.model.NotificationEvent exactly
 * (userId, title, message, channel) since the consumer deserializes
 * JSON straight into that class.
 */
public class NotificationEvent {

    private Integer userId;
    private String title;
    private String message;
    private String channel; // "email" | "mobileNo" | "deviceToken"

    public NotificationEvent() {
    }

    public NotificationEvent(Integer userId, String title, String message, String channel) {
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.channel = channel;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "NotificationEvent{userId=" + userId + ", title='" + title
                + "', message='" + message + "', channel='" + channel + "'}";
    }
}
