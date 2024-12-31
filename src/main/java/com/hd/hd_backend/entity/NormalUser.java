package com.hd.hd_backend.entity;

import java.util.List;

public class NormalUser extends User {

    private Integer weight;
    private Integer age;

    private Integer height;

    private Integer gender;

    private Double activityFactor;

    private Integer isBlocked;

    private String phone;

    // Getters and Setters


    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }



    public Integer getGender() {return gender;}

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Double getActivityFactor() {
        return activityFactor;
    }

    public void setActivityFactor(Double activityFactor) {
        this.activityFactor =activityFactor;
    }



    public Integer getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Integer isBlocked) {
        this.isBlocked = isBlocked;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public NormalUser(){
        this.profilePicture="https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800";
        this.isBlocked=0;};
    
    public void updateReminder() {
        // 更新提醒设置
    }
    

    public void viewPost(String postId) {
        // 查看帖子
    }
    
    public void commentOnPost(String postId, String content) {
        // 评论帖子
    }
    
    public void createPost(String title, String content) {
        // 创建帖子
    }
    
    public void viewComment(String commentId) {
        // 查看评论
    }
    

    
    public static NormalUser getUserById(String userId) {
        // 根据ID获取用户
        return null;
    }
    
    public static void addUser(NormalUser user) {
        // 添加用户
    }
    
    public static void removeUser(NormalUser user) {
        // 移除用户
    }
    
    public List<FoodRecord> viewFoodRecords() {
        // 查看食物记录
        return null;
    }
    
    public void recoverPassword(String newPassword) {
        this.password = newPassword;
    }
    
    public void setReminder(String reminderTime) {
        // 设置提醒
    }
    
    public void addViolation(String description) {
        // 添加违规记录
    }
    

    

    public void update(NormalUser updateInfo) {
        if (updateInfo.getName() != null && !updateInfo.getName().isEmpty()) {
            this.name = updateInfo.getName();
        }
        if (updateInfo.getPassword() != null && !updateInfo.getPassword().isEmpty()) {
            this.password = updateInfo.getPassword();
        }
        if (updateInfo.getProfilePicture() != null && !updateInfo.getProfilePicture().isEmpty()) {
            this.profilePicture = updateInfo.getProfilePicture();
        }
        if (updateInfo.getWeight() != null) {
            this.weight = updateInfo.getWeight();
        }
        if (updateInfo.getAge() != null) {
            this.age = updateInfo.getAge();
        }
        if (updateInfo.getHeight() != null) {
            this.height = updateInfo.getHeight();
        }
        if (updateInfo.getPhone() != null && !updateInfo.getPhone().isEmpty()) {
            this.phone = updateInfo.getPhone();
        }
        if (updateInfo.getGender() != null) {
            this.gender = updateInfo.getGender();
        }
        if (updateInfo.getActivityFactor() != null) {
            this.activityFactor = updateInfo.getActivityFactor();
        }
    }

} 