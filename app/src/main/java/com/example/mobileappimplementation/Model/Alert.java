package com.example.mobileappimplementation.Model;

import com.example.mobileappimplementation.Controller.APIDetails;

public class Alert extends APIDetails {
    private String heading,description;

    public Alert() {
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
