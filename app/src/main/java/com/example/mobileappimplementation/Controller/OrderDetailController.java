package com.example.mobileappimplementation.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mobileappimplementation.R;

import java.util.ArrayList;

public class OrderDetailController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);
        Bundle caller = getIntent().getExtras();
        Spinner droneOptionSpinner = (Spinner) findViewById(R.id.drone_option);
        ArrayList<String> droneList = new ArrayList<String>();
        droneList.add("Select Drone");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,droneList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        droneOptionSpinner.setAdapter(arrayAdapter);
        droneList.add("DJI Tello with 2 batteries - Rs 35000");
        droneOptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedOption = adapterView.getItemAtPosition(i).toString();
                TextView hiddenText = (TextView) findViewById(R.id.selected_option);
                hiddenText.setText(selectedOption);
                TextView errorDroneOption = findViewById(R.id.error_drone_option);
                if(!(errorDroneOption.toString().equals("Select Drone")))
                    errorDroneOption.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(caller != null){
            String droneOption = caller.getString("droneOption","0");
            String quantity = caller.getString("quantity","0");
            String address = caller.getString("address","0");
            EditText QuantityInput = findViewById(R.id.quantity);
            EditText AddressInput = findViewById(R.id.address);
            QuantityInput.setText(quantity);
            AddressInput.setText(address);
            droneList = new ArrayList<String>();
            droneList.add("DJI Tello with 2 batteries - Rs 35000");
            arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,droneList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            droneOptionSpinner.setAdapter(arrayAdapter);
            droneList.add("Select Drone");
        }
    }

    public void toOrderPage(View view) {
        finish();
    }


    public void toOrderVerificationPage(View view) {
        EditText inputQuantity = (EditText) findViewById(R.id.quantity);
        EditText inputAddress = (EditText)  findViewById(R.id.address);
        TextView errorDroneOption = (TextView) findViewById(R.id.error_drone_option);
        TextView errorQuantity = (TextView) findViewById(R.id.error_drone_quantity);
        TextView errorAddress = (TextView) findViewById(R.id.error_address);
        TextView errorAddressTwo = (TextView) findViewById(R.id.error_address_2);
        TextView hiddenText = (TextView) findViewById(R.id.selected_option);
        errorDroneOption.setTextColor(Color.parseColor("red"));
        errorQuantity.setTextColor(Color.parseColor("red"));
        errorAddress.setTextColor(Color.parseColor("red"));
        errorAddressTwo.setTextColor(Color.parseColor("red"));
        boolean verifyDroneOption = false, verifyQuantity = false, verifyAddress = false;
        if(hiddenText.getText().toString().equals("Select Drone")){
            errorDroneOption.setText("Please Select Drone.");
            if(verifyDroneOption)
                verifyDroneOption = false;
        }
        else {
            errorDroneOption.setText("");
            verifyDroneOption = true;
        }
        boolean conditionOne = isFieldEmpty(inputQuantity);  //check for quantity
        boolean conditionTwo = isFieldEmpty(inputAddress); //check for address
        if(conditionOne){
            errorQuantity.setText("Quantity field cannot be empty.");
            if(verifyQuantity)
                verifyDroneOption = false;
        }
        else {
            errorQuantity.setText("");
            verifyQuantity = true;
        }
        if (conditionTwo){
            errorAddressTwo.setText("");
            errorAddress.setText("Address field cannot be empty.");
            if(verifyAddress)
                verifyAddress = false;
        }
        else if(checkForAddress(inputAddress) != 0){
            if(verifyAddress)
                verifyAddress = false;
            errorAddress.setText("");
            int check = checkForAddress(inputAddress);
            if(check == 1){
                errorAddressTwo.setText("Address field cannot contain only numbers. It should be of the format House#, Street#, Colony, Area, City");
            }
            else if (check == 2){
                errorAddressTwo.setText("Address field cannot contain only letters. It should be of the format House#, Street#, Colony, Area, City");
            }
        }
        else{
            errorAddress.setText("");
            errorAddressTwo.setText("");
            verifyAddress = true;
        }
        if(verifyDroneOption && verifyQuantity && verifyAddress){ //insertion part begins
            String droneOption = hiddenText.getText().toString();
            String quantity = inputQuantity.getText().toString();
            String address = inputAddress.getText().toString();
            Intent goToOrderDetailVerification = new Intent();
            goToOrderDetailVerification.setClass(this,OrderVerificationController.class);
            goToOrderDetailVerification.putExtra("droneOption",droneOption);
            goToOrderDetailVerification.putExtra("quantity",quantity);
            goToOrderDetailVerification.putExtra("address",address);
            startActivity(goToOrderDetailVerification);
            //**code snippet to be used somewhere else**//
//            Customer customer = new Customer();
//            customer.setAddress(address);
//            OrderDetail orderDetail = new OrderDetail(quantity, address);
//            long millis = System.currentTimeMillis();
//            Date date = new Date(millis);
//            Order order = new Order(date,orderDetail,customer);
        }
    }

    private int checkForAddress(EditText inputAddress) {
        String input = inputAddress.getText().toString();
        if(isNumeric(input))
            return 1;
        else if (areLetters(input))
            return 2;
        else
            return 0;


    }
    public boolean isNumeric(String string){
        try {
            double d = Double.parseDouble(string);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public boolean areLetters(String string){
        if(string.matches("[a-zA-Z]+"))
            return true;
        return false;
    }

    public boolean isFieldEmpty(EditText editText){
        if(editText.getText().toString().equals(""))
            return true;
        return false;
    }

}