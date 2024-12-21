package com.hd.hd_backend.job;
import com.hd.hd_backend.entity.Reminder;
import com.hd.hd_backend.mapper.ReminderMapper;
import com.hd.hd_backend.utils.WebSocketSessionManager;
import com.hd.hd_backend.utils.JsonUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.PostConstruct;

@Component
public class ReminderCheckJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(ReminderCheckJob.class);

    @Autowired
    private ReminderMapper reminderMapper;

    public ReminderCheckJob() {
        logger.info("ReminderCheckJob无参构造函数被调用");
    }

    @PostConstruct
    public void init() {
        logger.info("ReminderCheckJob初始化完成，reminderMapper注入{}", 
            reminderMapper != null ? "成功" : "失败");
    }

    @Override
    public void execute(JobExecutionContext context) {
        logger.info("ReminderCheckJob开始执行...");
        
        try {
            // 获取当前时间，使用HH:mm:ss格式
            LocalTime now = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String currentTime = now.format(formatter);
            logger.info("当前时间: {}", currentTime);

            if (reminderMapper == null) {
                logger.error("reminderMapper未注入，无法执行任务");
                return;
            }

            // 获取所有激活的提醒
            List<Reminder> activeReminders = reminderMapper.findAllActiveReminders();
            logger.info("获取到{}个活动提醒", activeReminders.size());
            
            for (Reminder reminder : activeReminders) {
                logger.debug("处理提醒: reminderID={}, userId={}, time={}", 
                    reminder.getReminderId(), reminder.getUserId(), reminder.getTime());
                
                // 比较时间，只比较到分钟级别
                String reminderTimePrefix = reminder.getTime().substring(0, 5);
                String currentTimePrefix = currentTime.substring(0, 5);
                logger.debug("比较时间: 提醒时间={}, 当前时间={}", reminderTimePrefix, currentTimePrefix);

                if (reminderTimePrefix.equals(currentTimePrefix)) {
                    logger.info("发现需要执行的提醒: reminderID={}", reminder.getReminderId());
                    
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
                            logger.info("成功发送提醒消息给用户: {}", reminder.getUserId());
                        } catch (Exception e) {
                            logger.error("发送提醒消息失败: {}", e.getMessage(), e);
                        }
                    } else {
                        logger.warn("用户{}的WebSocket会话不存在或已关闭", reminder.getUserId());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("ReminderCheckJob执行出错: {}", e.getMessage(), e);
        }
        
        logger.info("ReminderCheckJob执行完成");
    }
}
