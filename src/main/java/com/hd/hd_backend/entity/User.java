package com.hd.hd_backend.entity;

public class User {
    Integer id;
    String  name;
    String password;
    String profilePicture;
    Integer isAdmin;
    String phone;

    public String getPhone() {
        return phone;
    }
public void setPhone(String phone) {
        this.phone = phone;
}
    public Integer getIsAdmin() {
        return isAdmin;
    }
    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
    public String getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
    public Integer getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public User() {

    }


}
