package com.partyhard.partyhard.domain;

public class User {

    private String username, avatar;
    private int id;

    public User(String username, String avatar, int id) {
        this.setId(id);
        this.setAvatar(avatar);
        this.setUsername(username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
