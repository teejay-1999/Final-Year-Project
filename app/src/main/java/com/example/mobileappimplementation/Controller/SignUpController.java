package com.example.mobileappimplementation.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mobileappimplementation.Model.User;
import com.example.mobileappimplementation.R;

import java.util.regex.Pattern;

public class SignUpController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
    }

    public void SignUp(View view) {
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
        boolean conditionFive = isFieldEmpty(inputPassword) || !(isMoreThanSixLetters(inputPassword.getText().toString()));
        boolean conditionSix =  isFieldEmpty(inputConfirmPassword);

        if(!conditionOne && !conditionTwo && !conditionThree && !conditionFour && !conditionFive && !conditionSix){
            String firstName = inputFirstName.getText().toString();
            String lastName = inputLastName.getText().toString();
            String email = inputEmail.getText().toString();
            String phoneNumber = inputPhoneNumber.getText().toString();
            String password = inputPassword.getText().toString();
            String confirmPassword = inputConfirmPassword.getText().toString();
            if(confirmPassword.equals(password)) {
                User user = new User(firstName, lastName, email, phoneNumber, password);
                user.insert(getApplicationContext());
                toDashboard(user);
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
                    inputLastName.setError("First Name should only be letters");
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
    public boolean isMoreThanSixLetters(String string){
            return (string.length() >= 6);
        }
    public void toLogin(View view){
        startActivity(new Intent(getApplicationContext(),LogInController.class));
    }
    public void toDashboard(User user){
        SharedPreferences preference = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString("firstName",user.getFirstName());
        editor.putString("lastName",user.getLastName());
        editor.putString("email",user.getEmail());
        editor.putString("phoneNumber",user.getPhoneNumber());
        editor.putString("password",user.getPassword());
        editor.commit();
        startActivity(new Intent(getApplicationContext(), MainActivityController.class));
    }
}