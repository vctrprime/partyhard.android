package com.partyhard.partyhard.domain;

public class Party {

    private String title, description, username;
    private int id;

    public Party(int id, String title, String description, String username){
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setUsername(username);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




}
