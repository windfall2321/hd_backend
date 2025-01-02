package com.hd.hd_backend.mapper;

import com.hd.hd_backend.entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {
    void insertNotification(Notification notification);
    void deleteNotification(@Param("notificationId") Integer notificationId);
    void updateNotification(Notification notification);
    List<Notification> getUserNotifications(@Param("userId") Integer userId);
    void markAsSent(@Param("notificationId") Integer notificationId);
}
