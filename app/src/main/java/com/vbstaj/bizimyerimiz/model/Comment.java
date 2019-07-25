package com.vbstaj.bizimyerimiz.model;

import java.util.Date;

public class Comment {
    private String name;
    private String content;
    private String userID;
    private Date createdAt;

    public Comment(String name,String content,String userID, Date createdAt){
        this.name = name;
        this.content = content;
        this.userID = userID;
        this.createdAt = createdAt;
    }

    public Comment(){}

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getUserID() {
        return userID;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
