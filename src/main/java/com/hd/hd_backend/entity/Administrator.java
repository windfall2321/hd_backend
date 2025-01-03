package com.hd.hd_backend.entity;

public class Administrator extends User {
    public Administrator() {
        this.isAdmin = 1;  // 管理员的isAdmin属性默认为1
    }
}
