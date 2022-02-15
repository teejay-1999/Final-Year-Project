package com.example.mobileappimplementation.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.mobileappimplementation.R;

public class OrderPlacementController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_placement);
    }

    public void toOrderPage(View view) {
        finish();
    }
}