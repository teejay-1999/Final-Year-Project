package com.example.mobileappimplementation.Model;

public class OrderDetail {
    private String quantity;
    private String droneOption;

    public OrderDetail(String quantity, String droneOption) {
        this.quantity = quantity;
        this.droneOption = droneOption;

    }


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDroneOption() {
        return droneOption;
    }

    public void setDroneOption(String droneOption) {
        this.droneOption = droneOption;
    }
}
