package com.example.mobileappimplementation.Controller;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.mobileappimplementation.R;

import java.util.ArrayList;

public class OrderPlacementController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_placement);
        Spinner droneOption = findViewById(R.id.drone_option);
        ArrayList<String> droneList = new ArrayList<String>();
        droneList.add("Select Drone");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,droneList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        droneOption.setAdapter(arrayAdapter);
        droneList.add("DJI Tello with 2 batteries - Rs 35000");
        droneOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedOption = adapterView.getItemAtPosition(i).toString();
                Button orderPlace = (Button) findViewById(R.id.complete_place_order);
                orderPlace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void toOrderPage(View view) {
        finish();
    }
}