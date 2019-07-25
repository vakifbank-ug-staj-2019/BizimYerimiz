package com.vbstaj.bizimyerimiz.model;

import java.util.Date;

public class Message {
    private String name;
    private String message;
    private String userID;
    private Date createdAt;

    public Message(String name,String message,String userID, Date createdAt){
        this.name = name;
        this.message = message;
        this.userID = userID;
        this.createdAt = createdAt;
    }

    public Message(){}

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getUserID() {
        return userID;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
