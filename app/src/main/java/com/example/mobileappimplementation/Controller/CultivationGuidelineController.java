package com.example.mobileappimplementation.Controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileappimplementation.Adapter.CultivationGuidelineAdapter;
import com.example.mobileappimplementation.MainActivity;
import com.example.mobileappimplementation.Model.CultivationGuideline;
import com.example.mobileappimplementation.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class CultivationGuidelineController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cultivation_guideline);
        displayGuidelines();
    }

    public void toDashboard(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void displayGuidelines(){
        CommonVariables commonVariables = new CommonVariables();
        retrieve(commonVariables);

    }
    public void dataToRecyclerView(JSONArray jsonArray){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CultivationGuidelineAdapter(jsonArray));
    }
    public void retrieve(CommonVariables commonVariables){
        commonVariables.setAPIName("cultivation_guideline_data.php");
        String completeURL = commonVariables.getUrl() + commonVariables.getAPIName();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, completeURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    dataToRecyclerView(jsonArray);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> mapObject = new HashMap<String, String>();
                return mapObject;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}