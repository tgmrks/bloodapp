package com.bloodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bloodapp.util.Utilities;

public class HomeActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences profilePref;

    private DrawerLayout mDrawerLayout;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Menu Button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white);

        //Actual Nav. Drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
     /*
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
*/


        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                        Log.i("TESTE", "SLIDE");
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                        Log.i("TESTE", "OPEN");
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                        Log.i("TESTE", "CLOSE");
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                        Log.i("TESTE", "CHANGE");

                    }
                }
        );

        profilePref = getSharedPreferences(Utilities.PREF_NAME, Context.MODE_PRIVATE);
        Log.i("TESTE", profilePref.getString(Utilities.NAME, ""));
        Log.i("TESTE", profilePref.getString(Utilities.SURENAME, ""));
        Log.i("TESTE", profilePref.getString(Utilities.EMAIL, ""));
        Log.i("TESTE", profilePref.getString(Utilities.PASSWORD, ""));
        Log.i("TESTE", profilePref.getString(Utilities.CONTACT, ""));
        Log.i("TESTE", profilePref.getString(Utilities.GENDER, ""));
        Log.i("TESTE", profilePref.getString(Utilities.BLOODTYPE, ""));
        Log.i("TESTE", profilePref.getString(Utilities.BIRTHDATE, ""));
        Log.i("TESTE", profilePref.getString(Utilities.ILLNESS, ""));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("TESTE", "on opt MENU");

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        /*
            case R.id.nav_home:
                mDrawerLayout.openDrawer(GravityCompat.START);//startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                return true;
            case R.id.nav_profile:
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                Log.i("TESTE", "PROFILE");
                return true;
            case R.id.nav_about:
                startActivity(new Intent(HomeActivity.this, AboutActivity.class));
                Log.i("TESTE", "ABOUT");
                return true;
            case R.id.nav_config:
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                Log.i("TESTE", "CONFIG");
                return true;
        */
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Log.i("TESTE", "on nav MENU");
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        }
        else if (id == R.id.nav_profile) {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            Log.i("TESTE", "PROFILE");
        }
        else if (id == R.id.nav_about) {
            startActivity(new Intent(HomeActivity.this, AboutActivity.class));
            Log.i("TESTE", "ABOUT");
        }
        else if (id == R.id.nav_config) {
            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
            Log.i("TESTE", "CONFIG");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
