package com.example.lab4_appiot_20203554.entity;

import java.io.Serializable;

public class Result implements Serializable {
    private Name name;
    private Login login;
    private String email;
    private Picture picture;
    private Location location;
    private String phone;
    private String gender;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String correo) {
        this.email = correo;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
