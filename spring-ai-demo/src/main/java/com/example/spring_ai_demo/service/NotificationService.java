package com.example.spring_ai_demo.service;

import com.example.spring_ai_demo.model.Notification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private final List<Notification> notifications = new ArrayList<>();

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public List<Notification> getAllNotifications() {
        return notifications;
    }
}
