package com.vbstaj.bizimyerimiz.model;

import java.util.Date;

public class User {

    private String name;
    private String surname;
    private boolean isAdmin;
    private Date birthdate;
    private Date registeredAt;
    private boolean gender; //0 for female 1 for male
    private String city;
    private String email;
    private String linkedinUsername;
    private String phoneNumber;

    public User(String name, String surname, Date birthdate, Date registeredAt, Boolean gender, String city, String email, String linkedinUsername, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.isAdmin = false;
        this.birthdate = birthdate;
        this.registeredAt = registeredAt;
        this.gender = gender;
        this.city = city;
        this.email = email;
        this.linkedinUsername = linkedinUsername;
        this.phoneNumber = phoneNumber;
    }



    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public boolean isGender() {
        return gender;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getLinkedinUsername() {
        return linkedinUsername;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


}
