package com.example.spring_ai_demo.controller;


import com.example.spring_ai_demo.model.Notification;
import com.example.spring_ai_demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    public String showNotificationsPage(Model model) {
        System.out.println("Notifications page is being accessed");
        model.addAttribute("notifications", notificationService.getAllNotifications());
        return "notifications";
    }

    @PostMapping("/notifications/add")
    public String addNotification(@RequestParam String message, @RequestParam String dateTime) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setDateTime(LocalDateTime.parse(dateTime));
        notificationService.addNotification(notification);
        return "redirect:/notifications";
    }
}
