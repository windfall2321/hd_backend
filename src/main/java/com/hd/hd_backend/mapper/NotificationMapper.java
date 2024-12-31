package com.hd.hd_backend.mapper;


import com.hd.hd_backend.entity.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotificationMapper {
    List<Notification> getAllUserNotification(@Param("user_id")Integer userId);
    List<Notification> getAllUserTypeNotification(@Param("user_id")Integer userId,Integer type);
    Notification getNoticicationById(Integer id);
    void insertNotification(Notification notification);
    void updateNotification(Notification notification);
    void deleteNotification(Integer id);

}
