package com.example.mobileappimplementation.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileappimplementation.Model.User;
import com.example.mobileappimplementation.ObjectListener.ResponseListener;
import com.example.mobileappimplementation.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.regex.Pattern;

public class LogInController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preference = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        if(preference.contains("email")){
            startActivity(new Intent(getApplicationContext(), MainActivityController.class));
        }
        setContentView(R.layout.log_in);

    }

    public void toSignUp(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpController.class));
    }

    public void Login(View view) {
        User user = null;
        EditText inputEmail = (EditText) findViewById(R.id.email);
        EditText inputPassword = (EditText) findViewById(R.id.password);
        boolean conditionOne = isFieldEmpty(inputEmail) || !(isCorrectEmailPattern(inputEmail.getText().toString()));
        boolean conditionTwo = isFieldEmpty(inputPassword) || !(isMoreThanSixLetters(inputPassword.getText().toString()));
        if(!conditionOne && !conditionTwo){
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            user = new User(email, password);
            user.ifUserExist(getApplicationContext());
            user.setListener(new ResponseListener() {
                @Override
                public void onResult(String result) {
                    if(result.equals("User Not Found") || result.equals("Credentials Did not Match")) {
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            toDashboard(jsonArray);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    inputEmail.getText().clear();
                    inputPassword.getText().clear();
                }
            });
        }
        else{
            if (conditionOne){
                conditionOne = isFieldEmpty(inputEmail);
                if(conditionOne)
                    inputEmail.setError("This field is required");
                else
                    inputEmail.setError("Email should be of the format user@domain.com");
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
    public boolean isMoreThanSixLetters(String string){

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
        editor.putString("firstName",jsonArray.getJSONObject(0).getString("first_name"));
        editor.putString("lastName",jsonArray.getJSONObject(0).getString("last_name"));
        editor.putString("email",jsonArray.getJSONObject(0).getString("email"));
        editor.putString("phoneNumber",jsonArray.getJSONObject(0).getString("phone_number"));
        editor.putString("password",jsonArray.getJSONObject(0).getString("password"));
        editor.commit();
        startActivity(new Intent(getApplicationContext(), MainActivityController.class));
    }
}