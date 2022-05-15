package com.example.mobileappimplementation.Handler;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileappimplementation.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;


public class WeatherAPIHandler {
    public void getCurrentWeatherInfo(String city, View view){
        String url = "http://api.weatherapi.com/v1/current.json?key=aaca1ef2295b4985a50162014221003&q="+city+"&aqi=no";
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                TextView cityName, temperature,day,forecast,forecastImage;
                cityName = (TextView) view.findViewById(R.id.city_name2);
                temperature = (TextView) view.findViewById(R.id.temperature2);
                day = (TextView) view.findViewById(R.id.day2);
//                forecast = (TextView) view.findViewById(R.id.forecast2);
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
