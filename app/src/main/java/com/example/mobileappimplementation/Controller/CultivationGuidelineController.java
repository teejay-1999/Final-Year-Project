package com.example.mobileappimplementation.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mobileappimplementation.Adapter.CultivationGuidelineAdapter;
import com.example.mobileappimplementation.Model.CultivationGuideline;
import com.example.mobileappimplementation.ObjectListener.ResponseListener;
import com.example.mobileappimplementation.R;

import org.json.JSONArray;
import org.json.JSONException;

public class CultivationGuidelineController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cultivation_guideline);
        displayGuidelines();
    }

    public void toDashboard(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivityController.class));
    }
    public void displayGuidelines(){
        CultivationGuideline cultivationGuideline = new CultivationGuideline();
        cultivationGuideline.retrieve(getApplicationContext());
        cultivationGuideline.setListener(new ResponseListener() {
            @Override
            public void onResult(String result) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    dataToRecyclerView(jsonArray);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void dataToRecyclerView(JSONArray jsonArray){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CultivationGuidelineAdapter(jsonArray));
    }
}