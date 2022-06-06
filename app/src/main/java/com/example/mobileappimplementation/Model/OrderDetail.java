package com.example.mobileappimplementation.Model;

public class OrderDetail {
    private String price;
    private String droneDescription;

    public OrderDetail(String price, String droneDescription) {
        this.price = price;
        this.droneDescription = droneDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDroneDescription() {
        return droneDescription;
    }

    public void setDroneDescription(String droneDescription) {
        this.droneDescription = droneDescription;
    }
}
