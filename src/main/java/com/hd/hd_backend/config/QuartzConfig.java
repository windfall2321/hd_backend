package com.hd.hd_backend.config;
import com.hd.hd_backend.job.ReminderCheckJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import jakarta.annotation.PostConstruct;
@Configuration
public class QuartzConfig {
    private static final Logger logger = LoggerFactory.getLogger(QuartzConfig.class);

    @PostConstruct
    public void init() {
        logger.info("QuartzConfig初始化");
    }

    @Bean
    public JobDetail reminderJobDetail() {
        logger.info("创建ReminderCheckJob的JobDetail");
        JobDetail jobDetail = JobBuilder.newJob(ReminderCheckJob.class)
                .withIdentity("reminderJob")
                .storeDurably()
                .requestRecovery(true)
                .build();
        logger.info("JobDetail创建成功: {}", jobDetail.getKey());
        return jobDetail;
    }

    @Bean
    public Trigger reminderJobTrigger() {
        logger.info("创建ReminderCheckJob的Trigger");
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(reminderJobDetail())
                .withIdentity("reminderTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                .build();
        logger.info("Trigger创建成功: {}", trigger.getKey());
        return trigger;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobDetail jobDetail, Trigger trigger) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setJobDetails(jobDetail);
        schedulerFactory.setTriggers(trigger);
        logger.info("SchedulerFactoryBean创建成功");
        return schedulerFactory;
    }
}
