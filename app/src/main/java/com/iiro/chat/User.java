package com.iiro.chat;

public class User {
    private String username, email, profileimg;

    public User(){

    }

    public User(String username, String email, String profileimg) {
        this.username = username;
        this.email = email;
        this.profileimg = profileimg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }
}
