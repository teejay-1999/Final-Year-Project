package com.example.mobileappimplementation.Controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import com.example.mobileappimplementation.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
    }

    public void SignUp(View view) {
        APIDetails commonVariables = new APIDetails();
        EditText inputFirstName = (EditText) findViewById(R.id.first_name);
        EditText inputLastName = (EditText) findViewById(R.id.last_name);
        EditText inputEmail = (EditText) findViewById(R.id.email);
        EditText inputPhoneNumber = (EditText) findViewById(R.id.phone_number);
        EditText inputPassword = (EditText) findViewById(R.id.password);
        EditText inputConfirmPassword = (EditText) findViewById(R.id.confirmPassword);

        boolean conditionOne = isFieldEmpty(inputFirstName) || !(areLetters(inputFirstName.getText().toString()));
        boolean conditionTwo = isFieldEmpty(inputLastName) || !(areLetters(inputLastName.getText().toString()));
        boolean conditionThree = isFieldEmpty(inputEmail) || !(isCorrectEmailPattern(inputEmail.getText().toString()));
        boolean conditionFour = isFieldEmpty(inputPhoneNumber) || !(isElevenDigits(inputPhoneNumber.getText().toString()));
        boolean conditionFive = isFieldEmpty(inputPassword) || !(isMoreThanSixCharacters(inputPassword.getText().toString()));
        boolean conditionSix =  isFieldEmpty(inputConfirmPassword);

        if(!conditionOne && !conditionTwo && !conditionThree && !conditionFour && !conditionFive && !conditionSix){
            String firstName = inputFirstName.getText().toString();
            String lastName = inputLastName.getText().toString();
            String email = inputEmail.getText().toString();
            String phoneNumber = inputPhoneNumber.getText().toString();
            String password = inputPassword.getText().toString();
            String confirmPassword = inputConfirmPassword.getText().toString();
            if(confirmPassword.equals(password)) {
                Customer customer = new Customer(firstName, lastName, email, phoneNumber, password);
                insert(customer, commonVariables);
                toDashboard(customer);
            }
            else{
                inputConfirmPassword.setError("Password did not match");
            }
        }
        else {
            if(conditionSix)
                inputConfirmPassword.setError("This field is required");
            if (conditionFive){
                conditionFive = isFieldEmpty(inputPassword);
                if(conditionFive) {
                    inputPassword.setError("This field is required");
                    if(!(conditionSix))
                        inputConfirmPassword.setText("");
                }
                else
                    inputPassword.setError("Password should be more than or equal to 6 characters");
            }
            if (conditionOne){
                conditionOne = isFieldEmpty(inputFirstName);
                if(conditionOne)
                    inputFirstName.setError("This field is required");
                else
                    inputFirstName.setError("First Name should only be letters");
            }
            if (conditionTwo){
                conditionTwo = isFieldEmpty(inputLastName);
                if(conditionTwo)
                    inputLastName.setError("This field is required");
                else
                    inputLastName.setError("Last Name should only be letters");
            }
            if (conditionThree){
                conditionThree = isFieldEmpty(inputEmail);
                if(conditionThree)
                    inputEmail.setError("This field is required");
                else
                    inputEmail.setError("Email should be of the format user@domain.com");
            }
            if (conditionFour){
                conditionFour = isFieldEmpty(inputPhoneNumber);
                if(conditionFour)
                    inputPhoneNumber.setError("This field is required");
                else
                    inputPhoneNumber.setError("Phone number should only be of 11 digits");
            }
        }

    }
    public boolean areLetters(String string){
        if(string.matches("[a-zA-Z]+"))
            return true;
        return false;
//        int length = string.length();
//        for(int i = 0, j = length - 1; j < i; i++, j--){
//            if(!(Character.isLetter(string.charAt(i))) || !(Character.isLetter(string.charAt(j))))
//                return false;
//        }
//        return true;
    }
    public boolean isFieldEmpty(EditText editText){
        if(editText.getText().toString().equals(""))
            return true;
        return false;
    }
    public boolean isCorrectEmailPattern(String string){
        String regularExpression = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        Pattern pattern = Pattern.compile(regularExpression);
        return pattern.matcher(string).matches();
    }
    public boolean isElevenDigits(String string){
        if(string.length() == 11)
            return true;
        return false;
    }
    public boolean isMoreThanSixCharacters(String string){
            return (string.length() >= 6);
        }
    public void toLogin(View view){
        startActivity(new Intent(getApplicationContext(),LogInController.class));
    }
    public void toDashboard(Customer customer){
        SharedPreferences preference = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString("firstName", customer.getFirstName());
        editor.putString("lastName", customer.getLastName());
        editor.putString("email", customer.getEmail());
        editor.putString("phoneNumber", customer.getPhoneNumber());
        editor.putString("password", customer.getPassword());
        editor.commit();
        retrieveCustomerId(customer.getEmail());
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void insert(Customer customer, APIDetails apiDetails){
        apiDetails.setAPIName("sign_up.php");
        String completeURL = apiDetails.getUrl() + apiDetails.getAPIName();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, completeURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> mapObject = new HashMap<String, String>();
                mapObject.put("firstName",customer.getFirstName());
                mapObject.put("lastName", customer.getLastName());
                mapObject.put("email", customer.getEmail());
                mapObject.put("password", customer.getPassword());
                mapObject.put("phoneNumber", customer.getPhoneNumber());
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

    public void retrieveCustomerId(String email){
        APIDetails apiDetails = new APIDetails();
        apiDetails.setAPIName("customer_id.php");
        String completeURL = apiDetails.getUrl() + apiDetails.getAPIName();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, completeURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    SharedPreferences preference = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preference.edit();
                    editor.putInt("id", jsonArray.getJSONObject(0).getInt("id"));
                    System.out.println(jsonArray.getJSONObject(0).getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> mapObject = new HashMap<String, String>();
                mapObject.put("email", email);
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