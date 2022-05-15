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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class LogInController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preference = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        if(preference.contains("email")){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        setContentView(R.layout.log_in);

    }

    public void toSignUp(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpController.class));
    }

    public void Login(View view) {
        APIDetails commonVariables = new APIDetails();
        Customer customer = null;
        EditText inputEmail = (EditText) findViewById(R.id.email);
        EditText inputPassword = (EditText) findViewById(R.id.password);
        boolean conditionOne = isFieldEmpty(inputEmail) || !(isCorrectEmailPattern(inputEmail.getText().toString()));
        boolean conditionTwo = isFieldEmpty(inputPassword) || !(isMoreThanSixCharacters(inputPassword.getText().toString()));
        if(!conditionOne && !conditionTwo){
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            customer = new Customer(email, password);
            ifUserExist(customer, commonVariables);
            inputEmail.getText().clear();
            inputPassword.getText().clear();
        }
        else{
            if (conditionOne){
                conditionOne = isFieldEmpty(inputEmail);
                if(conditionOne)
                    inputEmail.setError("This field is required");
                else
                    inputEmail.setError("Email should be of the format customer@domain.com");
            }
            if (conditionTwo){
                conditionTwo = isFieldEmpty(inputPassword);
                if(conditionTwo)
                    inputPassword.setError("This field is required");
                else {
                    inputPassword.setError("Password should be more than or equal to six digits");
                }
            }
        }

    }
    public boolean isCorrectEmailPattern(String string){
        String regularExpression = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        Pattern pattern = Pattern.compile(regularExpression);
        return pattern.matcher(string).matches();
    }
    public boolean isMoreThanSixCharacters(String string){

        return (string.length() >= 6);
    }
    public boolean isFieldEmpty(EditText editText){
        if(editText.getText().toString().equals(""))
            return true;
        return false;
    }

    public void toDashboard(JSONArray jsonArray) throws JSONException {
        SharedPreferences preference = getSharedPreferences("USER_DATA",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString("firstName",jsonArray.getJSONObject(0).getString("firstName"));
        editor.putString("lastName",jsonArray.getJSONObject(0).getString("lastName"));
        editor.putString("email",jsonArray.getJSONObject(0).getString("email"));
        editor.putString("phoneNumber",jsonArray.getJSONObject(0).getString("phoneNumber"));
        editor.putString("password",jsonArray.getJSONObject(0).getString("password"));
        editor.putInt("id", jsonArray.getJSONObject(0).getInt("id"));
        editor.commit();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void ifUserExist(Customer customer, APIDetails apiDetails){
        apiDetails.setAPIName("log_in.php");
        String completeURL = apiDetails.getUrl() + apiDetails.getAPIName();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, completeURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Customer Not Found") || response.equals("Credentials Did not Match")) {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        toDashboard(jsonArray);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                mapObject.put("email", customer.getEmail());
                mapObject.put("password", customer.getPassword());
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