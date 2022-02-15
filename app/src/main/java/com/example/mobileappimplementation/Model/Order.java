package com.example.mobileappimplementation.Model;

import com.example.mobileappimplementation.Controller.CommonVariables;

public class Order extends CommonVariables {
    private int id;
    private String date;

    public Order() {
    }

    public Order(int id, String date) {
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
