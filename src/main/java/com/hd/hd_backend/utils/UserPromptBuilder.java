package com.hd.hd_backend.utils;

import com.hd.hd_backend.entity.NormalUser;

public class UserPromptBuilder {
    
    /**
     * 构建包含用户信息的系统提示
     * @param user 用户信息
     * @return 个性化的系统提示
     */
    public static String buildUserSystemPrompt(NormalUser user) {
        StringBuilder prompt = new StringBuilder();
        
        prompt.append("你是一个专业的健康顾问，专门为").append(user.getName()).append("提供个性化的健康建议。\n\n");
        
        // 添加用户基本信息
        prompt.append("用户基本信息：\n");
        if (user.getAge() != null) {
            prompt.append("- 年龄：").append(user.getAge()).append("岁\n");
        }
        if (user.getGender() != null) {
            String gender = user.getGender() == 1 ? "男" : "女";
            prompt.append("- 性别：").append(gender).append("\n");
        }
        if (user.getHeight() != null) {
            prompt.append("- 身高：").append(user.getHeight()).append("cm\n");
        }
        if (user.getWeight() != null) {
            prompt.append("- 体重：").append(user.getWeight()).append("kg\n");
        }
        
        // 计算BMI
        if (user.getHeight() != null && user.getWeight() != null) {
            double heightInMeters = user.getHeight() / 100.0;
            double bmi = user.getWeight() / (heightInMeters * heightInMeters);
            prompt.append("- BMI：").append(String.format("%.1f", bmi)).append("\n");
            
            // 添加BMI分类
            String bmiCategory = getBMICategory(bmi);
            prompt.append("- 体重状态：").append(bmiCategory).append("\n");
        }
        
        if (user.getActivityFactor() != null) {
            String activityLevel = getActivityLevel(user.getActivityFactor());
            prompt.append("- 活动水平：").append(activityLevel).append("\n");
        }
        
        prompt.append("\n");
        
        // 添加专业指导
        prompt.append("请根据以上用户信息，提供个性化的健康建议\n");
        prompt.append("请用中文回答，语言要亲切友好，建议要具体实用。");
        
        return prompt.toString();
    }
    
    /**
     * 获取BMI分类
     */
    private static String getBMICategory(double bmi) {
        if (bmi < 18.5) {
            return "偏瘦";
        } else if (bmi < 24) {
            return "正常";
        } else if (bmi < 28) {
            return "超重";
        } else {
            return "肥胖";
        }
    }
    
    /**
     * 获取活动水平描述
     */
    private static String getActivityLevel(Double activityFactor) {
        if (activityFactor == null) {
            return "未设置";
        }
        
        if (activityFactor <= 1.2) {
            return "久坐不动";
        } else if (activityFactor <= 1.375) {
            return "轻度活动";
        } else if (activityFactor <= 1.55) {
            return "中度活动";
        } else if (activityFactor <= 1.725) {
            return "重度活动";
        } else {
            return "极重度活动";
        }
    }
} 