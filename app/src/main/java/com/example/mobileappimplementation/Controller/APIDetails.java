package com.example.mobileappimplementation.Controller;



public class APIDetails {
    private String url;
    private String APIName;


    public APIDetails() {

//        url = "http://192.168.18.239/api/";
        url = "https://aerialinspector.000webhostapp.com/api/";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAPIName() {
        return APIName;
    }

    public void setAPIName(String APIName) {
        this.APIName = APIName;
    }



}
