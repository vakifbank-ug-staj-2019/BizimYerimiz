package com.vbstaj.bizimyerimiz.model;

public class User {

    private String name;
    private String surname;

    public User(){}

    public User(String name,String surname){
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String makeItCensored(String text){
        return text.replaceAll("\\B.", "*");
    }

    public String getCensoredFullName(){
        return makeItCensored(this.name.trim()) + " " + makeItCensored(this.surname.trim());
    }
}
