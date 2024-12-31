package com.hd.hd_backend.dto;



import java.util.Date;

public class UserDTO {
    private String name;
    
    private String password;
    
    private Integer weight;
    
    private Integer age;
    
    private Integer height;
    
    private String phone;
    
    private boolean isLogin;

    private Date loginTime;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public Date getLoginTime() {return loginTime;}

    public void setLoginTime(Date logTime) {loginTime = logTime;}
} 