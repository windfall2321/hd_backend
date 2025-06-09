package com.hd.hd_backend.entity;

import java.util.List;

public class NormalUser extends User {

    private Double weight;
    private Integer age;

    private Integer height;

    private Integer gender;

    private Double activityFactor;

    private Integer isBlocked;

    private Integer point;

    // Getters and Setters

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
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

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public NormalUser(){
        this.profilePicture="https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800";
        this.isBlocked=0;
        this.isAdmin=0;
    };
    
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
        if (updateInfo.getPoint() != null) {
            this.point = updateInfo.getPoint();
        }
    }

} 