package com.example.mobileappimplementation.Controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.example.mobileappimplementation.Fragment.OrderFragmentController;
import com.example.mobileappimplementation.MainActivity;
import com.example.mobileappimplementation.R;
import com.google.android.material.navigation.NavigationView;

public class OrderVerificationController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_verification);
        Bundle caller = getIntent().getExtras();
        String droneOption = caller.getString("droneOption","0");
        String quantity = caller.getString("quantity","0");
        String address = caller.getString("address","0");
        TextView textDroneOption = findViewById(R.id.chosen_drone_option);
        TextView textQuantity = findViewById(R.id.entered_quantity);
        TextView textAddress = findViewById(R.id.entered_address);
        TextView actualDroneOption = findViewById(R.id.hidden_drone_option); //hidden
        TextView actualQuantity = findViewById(R.id.hidden_quantity); //hidden
        TextView actualAddress = findViewById(R.id.hidden_address); //hidden
        actualDroneOption.setText(droneOption);
        actualQuantity.setText(quantity);
        actualAddress.setText(address);
        textDroneOption.setText("Drone Selected: " + droneOption);
        textQuantity.setText("Quantity: " + quantity);
        textAddress.setText("Delivery Address: " + address);

    }

    public void toOrderDetailPage(View view) {
        TextView textDroneOption = findViewById(R.id.hidden_drone_option);
        TextView textQuantity = findViewById(R.id.hidden_quantity);
        TextView textAddress = findViewById(R.id.hidden_address);
        String droneOption = textDroneOption.getText().toString();
        String quantity = textQuantity.getText().toString();
        String address = textAddress.getText().toString();
        Intent goToOrderDetail = new Intent();
        goToOrderDetail.setClass(this,OrderDetailController.class);
        goToOrderDetail.putExtra("droneOption",droneOption);
        goToOrderDetail.putExtra("quantity",quantity);
        goToOrderDetail.putExtra("address",address);
        startActivity(goToOrderDetail);
    }

    public void toOrderPage(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sre you want to go back to the orders page? All the details you have entered will not be saved.");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent goToMainActivity = new Intent();
                goToMainActivity.setClass(getApplicationContext(),MainActivity.class);
                SharedPreferences preference = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();
                editor.putBoolean("orderCheck",true);
                editor.commit();
                startActivity(goToMainActivity);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void toOrderVerificationPage(View view) {}

}