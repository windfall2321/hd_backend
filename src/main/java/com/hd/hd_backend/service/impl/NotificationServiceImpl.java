package com.hd.hd_backend.service.impl;

import com.hd.hd_backend.entity.Notification;
import com.hd.hd_backend.mapper.NotificationMapper;
import com.hd.hd_backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public void createNotification(Notification notification) {
        notificationMapper.insertNotification(notification);
    }

    @Override
    public void deleteNotification(Integer notificationId) {
        notificationMapper.deleteNotification(notificationId);
    }

    @Override
    public void updateNotification(Notification notification) {
        notificationMapper.updateNotification(notification);
    }

    @Override
    public List<Notification> getUserNotifications(Integer userId) {
        return notificationMapper.getUserNotifications(userId);
    }

    @Override
    public void markAsSent(Integer notificationId) {
        notificationMapper.markAsSent(notificationId);
    }
} 