package com.example.mobileappimplementation.Model;

import com.example.mobileappimplementation.Controller.CommonVariables;

public class OrderStatus extends CommonVariables {
    private int id;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
