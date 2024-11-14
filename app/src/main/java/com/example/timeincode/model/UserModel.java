package com.example.timeincode.model;

public class UserModel {
    public String userName;
    public String profileImageUrl;
    public UserModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
