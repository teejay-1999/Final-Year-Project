package com.example.mobileappimplementation.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.mobileappimplementation.Fragment.DashboardFragment;
import com.example.mobileappimplementation.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivityController extends AppCompatActivity {

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
        TextView nameText = (TextView) findViewById(R.id.nameText);
        String firstName = preference.getString("firstName","0");
        DashboardFragment initialFragment = new DashboardFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,initialFragment).commit();
        initialFragment.setFirstName(firstName);
        //setNameOnDashboard();
        navigationView.setCheckedItem(R.id.home);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        DashboardFragment temp = new DashboardFragment();
                        navigationView.setCheckedItem(R.id.home);
                        temp.setFirstName(firstName);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,temp).commit();
                        break;
                    case R.id.log_out:
                        startActivity(new Intent(getApplicationContext(), LogInController.class));
                        editor.clear();
                        editor.commit();
                        break;
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
        startActivity(new Intent(getApplicationContext(),CultivationGuidelineController.class));
    }

    public void setNameOnDashboard(){
        SharedPreferences preference = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        TextView nameText = (TextView) findViewById(R.id.nameText);
        String firstName = preference.getString("firstName","0");
        nameText.setText("Welcome " + firstName);
    }
}