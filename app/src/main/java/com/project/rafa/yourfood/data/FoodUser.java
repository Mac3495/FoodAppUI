package com.project.rafa.yourfood.data;

import java.io.Serializable;

public class FoodUser implements Serializable {

    private String user;
    private String password;
    private String mail;
    private String description;
    private String userId;

    public FoodUser(){

    }

    public FoodUser(String user, String password, String mail, String description, String userId) {
        this.user = user;
        this.password = password;
        this.mail = mail;
        this.description = description;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
