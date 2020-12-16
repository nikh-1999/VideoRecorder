package com.example.videorecorder.Model;

public class Users {
    private String userId;
    private String fullName;
    private String eMail;

    public Users(String userId, String fullName, String eMail) {
        this.userId = userId;
        this.fullName = fullName;
        this.eMail = eMail;
    }

    public Users() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
