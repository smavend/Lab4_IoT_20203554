package com.example.lab4_appiot_20203554.entity;

import java.io.Serializable;

public class Name implements Serializable {
    private String title;
    private String last;
    private String first;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullName(){
        return title+" "+first+""+last;
    }
}
