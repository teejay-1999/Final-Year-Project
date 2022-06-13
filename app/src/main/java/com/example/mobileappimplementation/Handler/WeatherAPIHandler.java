package com.example.mobileappimplementation.Handler;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileappimplementation.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


public class WeatherAPIHandler {
    public void getCurrentWeatherInfo(String city, Context context, View view){
        String url = "http://api.weatherapi.com/v1/current.json?key=aaca1ef2295b4985a50162014221003&q="+city+"&aqi=no";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                TextView cityName, temperature,day,forecast,droneInspectionMessage;
                ImageView forecastImage;
                cityName = (TextView) view.findViewById(R.id.city_name2);
                temperature = (TextView) view.findViewById(R.id.temperature2);
                day = (TextView) view.findViewById(R.id.day2);
                forecast = (TextView) view.findViewById(R.id.condition_text2);
                day.setText("");
                droneInspectionMessage = (TextView) view.findViewById(R.id.drone_inspection_message2);
                forecastImage = (ImageView) view.findViewById(R.id.forecast_image2);
                try {
                    cityName.setText(response.getJSONObject("location").getString("name"));
                    temperature.setText(response.getJSONObject("current").getString("temp_c") + "°C");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE");
                    Date date = new Date();
                    String dayOfWeek = simpleDateFormat.format(date);
                    LocalDate currentDate = LocalDate.now();
                    String month = currentDate.getMonth().toString();
                    month = month.toLowerCase();
                    String firstLetter = month.substring(0,1);
                    String remainingLetter = month.substring(1);
                    firstLetter = firstLetter.toUpperCase();
                    month = firstLetter + remainingLetter;
                    dayOfWeek += ", " + currentDate.getDayOfMonth() + " " + month + " " + currentDate.getYear();
                    day.setText(dayOfWeek);
                    String isDayStatus = response.getJSONObject("current").getString("is_day");
//                    String isDayStatus = "1"; //for day night check (for the presentation)
                    String conditionText = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    System.out.println(conditionText);
                    String temp = conditionText;
                    String [] tempArray = temp.split(" ");
                    forecast.setText(conditionText);
                    if(tempArray.length == 1){
                        forecast.setTranslationX(700);
                    }
                    else {
                        forecast.setTranslationX(630);
                    }
                    conditionText.toLowerCase();
                    if(conditionText.contains("rain") && tempArray.length > 1){
                        forecast.setText("Possible rain");
                    }
                    if(conditionText.contains("thunderstorm") && tempArray.length > 1){
                        forecast.setText("Possible thunderstorm");
                    }
                    Picasso.get().load("https:" + response.getJSONObject("current").getJSONObject("condition").getString("icon")).into(forecastImage);
                    boolean conditionOne = !(conditionText.contains("rain")) && !(conditionText.contains("thunderstorm")) && !(conditionText.contains("precipitation")) && !(conditionText.contains("snow")) && !(conditionText.contains("wind"));
                    boolean conditionTwo = isDayStatus.equals("1");
                    if(conditionOne && conditionTwo){
                        droneInspectionMessage.setTextColor(Color.parseColor("green"));
                        droneInspectionMessage.setText("Drone Inspection is recommended");
                        droneInspectionMessage.setTranslationX(70);
                    }
                    else{
                        droneInspectionMessage.setTextColor(Color.parseColor("red"));
                        droneInspectionMessage.setText("Drone Inspection is not recommended");
                        droneInspectionMessage.setTranslationX(30);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
