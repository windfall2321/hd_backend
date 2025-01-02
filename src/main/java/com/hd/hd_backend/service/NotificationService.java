package com.hd.hd_backend.service;

import com.hd.hd_backend.entity.Notification;
import java.util.List;

public interface NotificationService {
    void createNotification(Notification notification);
    void deleteNotification(Integer notificationId);
    void updateNotification(Notification notification);
    List<Notification> getUserNotifications(Integer userId);
    void markAsSent(Integer notificationId);
} 