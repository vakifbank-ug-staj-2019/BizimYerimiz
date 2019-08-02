package com.vbstaj.bizimyerimiz.model;

import java.util.Date;

public class User implements Comparable<User> {

    private String name;
    private String surname;
    private boolean admin;
    private Date birthdate;
    private Date registeredAt;
    private String city;
    private String email;
    private String linkedinUsername;
    private String phoneNumber;

    public User(){};

    public User(String name, String surname, Date birthdate, Date registeredAt, String city, String email, String linkedinUsername, String phoneNumber,Boolean admin) {
        this.name = name;
        this.surname = surname;
        this.admin = admin;
        this.birthdate = birthdate;
        this.registeredAt = registeredAt;
        this.city = city;
        this.email = email;
        this.linkedinUsername = linkedinUsername;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int compareTo(User user) {
        return user.getRegisteredAt().compareTo(getRegisteredAt());
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public boolean isAdmin() {
        return admin;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLinkedinUsername(String linkedinUsername) {
        this.linkedinUsername = linkedinUsername;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
