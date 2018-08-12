package com.partyhard.partyhard.domain;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Party {

    private int id;
    private String title, description, image;
    private Float longitude, latitude, distance;
    private Date dateStart, dateFinish;

    private ArrayList<ToJoinRequest> requests;
    private User user;

    public Party(int id, String title, String description,
                 String image, Float longitude, Float latitude, Float distance,
                 Date dateStart, Date dateFinish, User user, ArrayList<ToJoinRequest> requests){
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setImage(image);
        this.setLongitude(longitude);
        this.setLatitude(latitude);
        this.setDistance(distance);
        this.setDateStart(dateStart);
        this.setDateFinish(dateFinish);
        this.setUser(user);
        this.setRequests(requests);
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

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateFinish() {
        return dateFinish;
    }

    public List<ToJoinRequest> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<ToJoinRequest> requests) {
        this.requests = requests;
    }

    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
