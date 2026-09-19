package com.e_learning.services;

public interface WelcomeNotificationService {
	
	void sendWelcomeNotification(Integer userId);
	void sendWelcomeNotification(Integer userId, String title, String message);

}
