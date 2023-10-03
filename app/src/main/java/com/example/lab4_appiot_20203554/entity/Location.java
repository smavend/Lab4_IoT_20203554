package com.example.lab4_appiot_20203554.entity;

import java.io.Serializable;

public class Location implements Serializable {
    private String city;
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
