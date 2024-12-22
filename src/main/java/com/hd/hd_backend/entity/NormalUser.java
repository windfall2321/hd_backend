package com.hd.hd_backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "普通用户实体")
public class NormalUser {
    @Schema(description = "用户ID")
    private Integer userId;
    
    @Schema(description = "用户名")
    private String name;
    
    @Schema(description = "密码")
    private String password;
    
    @Schema(description = "头像URL")
    private String profilePicture;
    
    @Schema(description = "体重(kg)")
    private Integer weight;
    
    @Schema(description = "年龄")
    private Integer age;
    
    @Schema(description = "身高(cm)")
    private Integer height;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "活动因子")
    private Integer activity_factor;
    
    @Schema(description = "是否被封禁(0:否 1:是)")
    private Integer isblocked;
    
    @Schema(description = "手机号")
    private String phone;

    // Getters and Setters


    public Integer getUserId() {
        return userId;
    }
public void setUserId(Integer userId) {
        this.userId = userId;
}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

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

    public Integer getGender() {return gender;}

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getActivityFactor() {
        return activity_factor;
    }

    public void setActivityFactor(Integer activity_factor) {
        this.activity_factor =activity_factor;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getIsblocked() {
        return isblocked;
    }

    public void setIsblocked(Integer isblocked) {
        this.isblocked = isblocked;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean login(String password) {
        return this.password.equals(password);
    }
    
    public void register() {
        this.isblocked = 0;
        // 注册逻辑
    }
    
    public void updateReminder() {
        // 更新提醒设置
    }
    
    public void changeInfo(String name, Integer age, Integer weight, Integer height, String phone) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.phone = phone;
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
    

    
    public boolean addFoodRecord(FoodItem foodItem) {
        // 添加食物记录
        return true;
    }
    

    
    public void addExercise(String exerciseType, int duration) {
        // 添加运动记录
    }

} 