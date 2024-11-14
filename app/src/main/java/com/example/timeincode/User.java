package com.example.timeincode;

public class User {
    private String uid;
    private String userName;

    public User() {
        // Firebase Realtime Database에서 데이터 객체를 생성하기 위해 빈 생성자가 필요합니다.
    }

    public User(String uid, String userName) {
        this.uid = uid;
        this.userName = userName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
