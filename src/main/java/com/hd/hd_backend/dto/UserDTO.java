package com.hd.hd_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(description = "用户注册登录DTO")
public class UserDTO {
    @Schema(description = "用户名")
    private String name;
    
    @Schema(description = "密码")
    private String password;
    
    @Schema(description = "体重(kg)")
    private Integer weight;
    
    @Schema(description = "年龄")
    private Integer age;
    
    @Schema(description = "身高(cm)")
    private Integer height;
    
    @Schema(description = "手机号")
    private String phone;
    
    @Schema(description = "是否为登录请求")
    private boolean isLogin;

    @Schema(description = "是否为登录请求")
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