package com.example.mobileappimplementation.Model;

public class Weather {
    private String day,time,forecastImageURL,cityName,temperature,forecast;

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getForecastImageURL() {
        return forecastImageURL;
    }

    public void setForecastImageURL(String forecastImageURL) {
        this.forecastImageURL = forecastImageURL;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
