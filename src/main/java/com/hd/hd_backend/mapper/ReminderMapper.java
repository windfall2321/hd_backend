package com.hd.hd_backend.mapper;

import com.hd.hd_backend.entity.Reminder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ReminderMapper {
    List<Reminder> findAllActiveReminders();

    void updateReminderStatus(Integer reminderId, Integer isOn);
}
