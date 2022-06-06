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
import com.example.mobileappimplementation.Adapter.DroneAdapter;
import com.example.mobileappimplementation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DroneController extends AppCompatActivity implements DroneAdapter.OnNoteListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drone);
        retrieve(new APIDetails(),1, 0);
    }

    public void retrieve(APIDetails apiDetails, int val, int position){
        apiDetails.setAPIName("drone_data.php");
        String completeURL = apiDetails.getUrl() + apiDetails.getAPIName();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, completeURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if(val == 1)
                        dataToRecyclerView(jsonArray);
                    else{
                        JSONObject jsonObject = jsonArray.getJSONObject(position);
                        String droneName = jsonObject.getString("Heading");
                        String droneDescription = jsonObject.getString("DroneDescription");
                        String price = jsonObject.getString("price");
                        System.out.print(droneName);
                        Intent goToOrderDetail = new Intent();
                        goToOrderDetail.setClass(getApplicationContext(),OrderDetailController.class);
                        goToOrderDetail.putExtra("droneName",droneName);
                        goToOrderDetail.putExtra("droneDescription",droneDescription);
                        goToOrderDetail.putExtra("price",price);
                        startActivity(goToOrderDetail);
                    }

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
    public void dataToRecyclerView(JSONArray jsonArray){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDrone);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DroneAdapter(jsonArray,this));
    }

    public void proceedToOrderDetails(View view) {
    }

    public void toOrderPage(View view) {
        finish();
    }

    @Override
    public void onNoteClick(int position) {
        APIDetails apiDetails = new APIDetails();
        retrieve(apiDetails, 2, position);
    }

    public void toOrderListPage(View view) {
        finish();
    }
}