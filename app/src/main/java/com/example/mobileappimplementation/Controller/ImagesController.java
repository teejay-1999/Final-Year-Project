package com.example.mobileappimplementation.Controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.mobileappimplementation.MainActivity;
import com.example.mobileappimplementation.Model.Customer;
import com.example.mobileappimplementation.Model.Order;
import com.example.mobileappimplementation.Model.OrderDetail;
import com.example.mobileappimplementation.R;

import java.sql.Date;

public class ImagesController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_controller);
    }

    public void showHealthyMessage(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This wheat crop is healthy");
        builder.setCancelable(false);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showRustMessage(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This wheat crop is contaminated with yellow rust crop");
        builder.setCancelable(false);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}