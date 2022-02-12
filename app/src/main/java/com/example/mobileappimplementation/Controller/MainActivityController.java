package com.example.mobileappimplementation.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mobileappimplementation.R;

public class MainActivityController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        SharedPreferences preference = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        TextView nameText = (TextView) findViewById(R.id.text);
        String firstName = preference.getString("firstName","0");
        nameText.setText("Welcome " + firstName);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void toCultivationGuidelines(View view) {
        startActivity(new Intent(getApplicationContext(),CultivationGuidelineController.class));
    }
}