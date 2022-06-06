package com.example.mobileappimplementation.Controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileappimplementation.MainActivity;
import com.example.mobileappimplementation.Model.Customer;
import com.example.mobileappimplementation.Model.Order;
import com.example.mobileappimplementation.Model.OrderDetail;
import com.example.mobileappimplementation.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderDetailController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);
        Bundle caller = getIntent().getExtras();
        String droneName = caller.getString("droneName");
        String price = caller.getString("price");
        TextView droneNameTextView = findViewById(R.id.drone_heading_detail);
        TextView dronePriceTextView = findViewById(R.id.drone_price_detail);
        droneNameTextView.setText("Drone Selected: " + droneName);
        dronePriceTextView.setText("Price: Rs " + price);
    }

    public void toDronePage(View view) {
        finish();
    }


    public void placeOrder(View view) {
        EditText inputAddress = (EditText)  findViewById(R.id.address);
        TextView errorAddress = (TextView) findViewById(R.id.error_address);
        TextView errorAddressTwo = (TextView) findViewById(R.id.error_address_2);
        errorAddress.setTextColor(Color.parseColor("red"));
        errorAddressTwo.setTextColor(Color.parseColor("red"));
        boolean verifyAddress = false;
        boolean conditionOne = isFieldEmpty(inputAddress); //check for address
        if (conditionOne){
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
        if(verifyAddress){ //sending this to the verification page
            String address = inputAddress.getText().toString();
            Bundle caller = getIntent().getExtras();
            String droneName = caller.getString("droneName");
            String droneDescription = caller.getString("droneDescription");
            String price = caller.getString("price");
            String addressCheck = "Please verify your address before you proceed to place order \n" + "Address: " + inputAddress.getText().toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(addressCheck);
            builder.setCancelable(false);
            builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent goToMainActivity = new Intent();
                    goToMainActivity.setClass(getApplicationContext(), MainActivity.class);
                    SharedPreferences preference = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preference.edit();
                    editor.putBoolean("placeOrderCheck", true);
                    editor.putInt("price", Integer.parseInt(price));
                    editor.commit();
                    Customer customer = new Customer();
                    customer.setAddress(address);
                    customer.setId(preference.getInt("id",0));
                    String temp = droneName +"- " + droneDescription;
                    System.out.print(temp);
                    OrderDetail orderDetail = new OrderDetail(price, temp);
                    long millis = System.currentTimeMillis();
                    Date date = new Date(millis);
                    Order order = new Order(date,orderDetail,customer);
                    APIDetails apiDetails = new APIDetails();
                    insert(order, apiDetails);
                    startActivity(goToMainActivity);
                }
            });
            builder.setNegativeButton("Change Address", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            //**code snippet to be used somewhere else**//

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

    public void insert(Order order, APIDetails apiDetails){
        apiDetails.setAPIName("insert_order_details.php");
        String completeURL = apiDetails.getUrl() + apiDetails.getAPIName();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, completeURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> mapObject = new HashMap<String, String>();
                //will look into these soon
                mapObject.put("customerId", Integer.toString(order.getCustomer().getId()));
                mapObject.put("address",order.getCustomer().getAddress());
                mapObject.put("date",order.getDate().toString());
                mapObject.put("status",order.getStatus());
                mapObject.put("price",order.getOrderDetail().getPrice());
                mapObject.put("droneDescription", order.getOrderDetail().getDroneDescription());
                mapObject.put("check", "1");
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