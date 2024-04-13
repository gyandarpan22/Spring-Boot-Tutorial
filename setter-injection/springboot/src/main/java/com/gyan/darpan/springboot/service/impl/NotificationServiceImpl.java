package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.service.NotificationService;
import org.springframework.stereotype.Component;

@Component
public class NotificationServiceImpl implements NotificationService {
    @Override
    public String getNotification() {
        return "You have 1 new notification";
    }
}
