package com.hd.hd_backend.job;
import com.hd.hd_backend.entity.Reminder;
import com.hd.hd_backend.mapper.ReminderMapper;
import com.hd.hd_backend.utils.WebSocketSessionManager;
import com.hd.hd_backend.utils.JsonUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
@Component
public class ReminderCheckJob implements Job {

    @Autowired
    private ReminderMapper reminderMapper;

    @Override
    public void execute(JobExecutionContext context) {
        // 获取当前时间
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String currentTime = now.format(formatter);

        // 获取所有激活的提醒
        List<Reminder> activeReminders = reminderMapper.findAllActiveReminders();

        for (Reminder reminder : activeReminders) {
            if (reminder.getTime().equals(currentTime)) {
                // 构造提醒消息
                Map<String, Object> reminderMessage = new HashMap<>();
                reminderMessage.put("type", 1);
                reminderMessage.put("message", "提醒");
                reminderMessage.put("user_id", reminder.getUserId());

                // 获取用户的WebSocket会话并发送消息
                WebSocketSession session = WebSocketSessionManager.getSession(reminder.getUserId());
                if (session != null && session.isOpen()) {
                    try {
                        session.sendMessage(new TextMessage(JsonUtils.toJson(reminderMessage)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
