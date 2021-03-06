package com.example.mobileappimplementation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.number.IntegerWidth;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.mobileappimplementation.Controller.CultivationGuidelineController;
import com.example.mobileappimplementation.Controller.DroneController;
import com.example.mobileappimplementation.Controller.LogInController;
import com.example.mobileappimplementation.Handler.ImageInspectionAPIHandler;
import com.example.mobileappimplementation.Handler.WeatherAPIHandler;
import com.example.mobileappimplementation.Fragment.AlertFragmentController;
import com.example.mobileappimplementation.Fragment.DashboardFragment;
import com.example.mobileappimplementation.Fragment.OrderFragmentController;
import com.example.mobileappimplementation.Fragment.SprayInformationFragmentController;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        NavigationView navigationView;
        ActionBarDrawerToggle actionBarDrawerToggle;
        DrawerLayout drawerLayout;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        SharedPreferences preference = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        String firstName = preference.getString("firstName","0");
        //if the user clicks on return to orders page while verifying order details//
        boolean check = preference.getBoolean("placeOrderCheck",false);
        if(check) {
            int price = preference.getInt("price",0);
            preference.edit().remove("placeOrderCheck").commit();
            preference.edit().remove("price").commit();
            navigationView.setCheckedItem(R.id.drone_order);
            OrderFragmentController orderFragmentController = new OrderFragmentController();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, orderFragmentController).commit();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Your order has been placed successfully. You will receive a tracking number from TCS soon. Your order will be delivered within 2 - 3 days. Please pay " + price + " to the delivery person.");
            builder.setCancelable(false);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        //code snippet ends//
        else {
            DashboardFragment initialFragment = new DashboardFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, initialFragment).commit();
            initialFragment.setFirstName(firstName);
            navigationView.setCheckedItem(R.id.home);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        DashboardFragment temp = new DashboardFragment();
                        navigationView.setCheckedItem(R.id.home);
                        temp.setFirstName(firstName);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).commit();
                        break;
                    case R.id.log_out:
                        startActivity(new Intent(getApplicationContext(), LogInController.class));
                        editor.clear();
                        editor.commit();
                        break;
                    case R.id.alerts:
                        AlertFragmentController alertFragmentController = new AlertFragmentController();
                        navigationView.setCheckedItem(R.id.alerts);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, alertFragmentController).commit();
                        break;
                    case R.id.drone_order:
                        navigationView.setCheckedItem(R.id.drone_order);
                        OrderFragmentController orderFragmentController = new OrderFragmentController();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, orderFragmentController).commit();
                        break;
                    case R.id.spray_information:
                        navigationView.setCheckedItem(R.id.spray_information);
                        SprayInformationFragmentController sprayInformationFragmentController = new SprayInformationFragmentController();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, sprayInformationFragmentController).commit();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void toCultivationGuidelines(View view) {
        startActivity(new Intent(getApplicationContext(), CultivationGuidelineController.class));
    }


    public void updateCurrentWeatherForecast(View view) {
        checkIfLocationIsEnabled();
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        boolean conditionOne = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        boolean conditionTwo = ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        String [] stringArray = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if(conditionOne && conditionTwo){
            ActivityCompat.requestPermissions(this,stringArray,100);
        }
        else{
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if(location != null){
                        try {
                            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            SharedPreferences preference = getSharedPreferences("USER_DATA",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preference.edit();
                            editor.putString("location",addressList.get(0).getLocality());
                            editor.putBoolean("locationCheck", true);
                            editor.commit();
                            WeatherAPIHandler weatherAPIHandler = new WeatherAPIHandler();
                            weatherAPIHandler.getCurrentWeatherInfo(addressList.get(0).getLocality(), getApplicationContext(), MainActivity.this.findViewById(R.id.dashboard));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public void toDroneSelectionPage(View view) {
        startActivity(new Intent(getApplicationContext(), DroneController.class));
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void captureImage(View view) {
        boolean pick = true;
        if(pick){
            if(!checkCameraPermission()){
                requestCameraPermission();
            }
            else{
                openDialogBoxForImageInspection();
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        String [] storageRequest = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermissions(storageRequest, 400);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        String [] cameraRequest = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermissions(cameraRequest, 200);
    }

    private boolean checkStoragePermission() {
        boolean checkOne = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return checkOne;
    }

    private void checkIfLocationIsEnabled() {
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        try{
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!gpsEnabled && !networkEnabled){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Aerial Inspector would want to access your location");
            builder.setCancelable(false);
            builder.setPositiveButton("Allow Access", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            builder.setNegativeButton("Deny Access", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    public void toDroneInspectionPage(View view) {
        TextView droneInspectionText = (TextView) findViewById(R.id.drone_inspection_message2);
        if(droneInspectionText.getText().toString().equals("Drone Inspection is not recommended")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Drone Inspection is not recommended at the moment. Do you still want to proceed for drone inspection?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Map work to be done
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
    }

    private boolean checkCameraPermission() {
        boolean checkOne = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean checkTwo = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return checkOne && checkTwo;

    }

    public void openCamera(View view){
        final int REQUEST_CODE = 42;
        Intent cameraOpener = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraOpener, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 42){
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Intent toImageInspection = new Intent();
                toImageInspection.setClass(getApplicationContext(), ImageInspectionAPIHandler.class);
                toImageInspection.putExtra("imageBitmapData", photo);
                startActivity(toImageInspection);
            }
            else{
                Uri photo = data.getData();
                Intent toImageInspection = new Intent();
                toImageInspection.setClass(getApplicationContext(), ImageInspectionAPIHandler.class);
                toImageInspection.putExtra("imageUriData", photo);
                startActivity(toImageInspection);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 200:{
                if(grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted && storageAccepted){
                        openDialogBoxForImageInspection();
                    }
                }
            }
            break;
        }
    }
    public void openDialogBoxForImageInspection(){
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialgo_box_image_inspection);
        dialog.show();
    }

    public void openGallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),52);
    }

}

