package com.partyhard.partyhard.domain;

public class ToJoinRequest {
    private int id, response, sex;

    public ToJoinRequest(int id, int response, int sex){
        this.setId(id);
        this.setResponse(response);
        this.setSex(sex);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }
}
