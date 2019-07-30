package com.vbstaj.bizimyerimiz.model;

import java.util.Date;

public class Comment {
    private String name;
    private String content;
    private String userID;
    private Date createdAt;
    private String title;

    public Comment(String name,String surname,String title, String content,String userID, Date createdAt){
        this.name = getCensoredFullName(name,surname);
        this.content = content;
        this.userID = userID;
        this.createdAt = createdAt;
        this.title = title;
    }

    public Comment(){}


    public String getTitle() { return title; }

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

    public String makeItCensored(String text){
        return text.replaceAll("\\B.", "*");
    }

    private String getCensoredFullName(String name,String surname){
        return makeItCensored(name.trim()) + " " + makeItCensored(surname.trim());
    }
}
