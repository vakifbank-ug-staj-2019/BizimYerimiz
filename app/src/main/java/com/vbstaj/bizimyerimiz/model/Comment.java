package com.vbstaj.bizimyerimiz.model;

import java.util.Date;

public class Comment implements Comparable<Comment> {
    private String name;
    private String content;
    private String userID;
    private Date createdAt;
    private String title;
    private float rating;

    public Comment(String name,String surname,String title, String content,String userID, Date createdAt,float rating){
        this.name = getCensoredFullName(name,surname);
        this.content = content;
        this.userID = userID;
        this.createdAt = createdAt;
        this.title = title;
        this.rating = rating;
    }

    public Comment(){}

    @Override
    public int compareTo(Comment comment) {
        return comment.getCreatedAt().compareTo(getCreatedAt());
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
