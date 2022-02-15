package com.example.mobileappimplementation.Controller;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.android.volley.toolbox.StringRequest;
import com.example.mobileappimplementation.ObjectListener.ResponseListener;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.io.StringReader;

public class CommonVariables {
    private String url;
    private String APIName;
    protected JSONArray jsonArray;
    private ResponseListener listener;

    public ResponseListener getListener() {
        return listener;
    }

    public CommonVariables() {

        url = "http://192.168.18.239/api/";
        jsonArray = null;
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

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }


    public void setListener(ResponseListener listener) {
        this.listener = listener;
    }
}
