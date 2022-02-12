package com.example.mobileappimplementation.Model;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.android.volley.toolbox.StringRequest;
import com.example.mobileappimplementation.ObjectListener.ResponseListener;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.io.StringReader;

public abstract class AbstractModel {
    private String url;
    private String APIName;
    protected JSONArray jsonArray;
    protected ResponseListener listener;
    public void insert(Context applicationContext){}
    public void delete(){}
    public void update(){}
    public void retrieve(Context applicationContext){}


    public AbstractModel() {

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
