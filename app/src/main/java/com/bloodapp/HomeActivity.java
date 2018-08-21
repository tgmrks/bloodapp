package com.bloodapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bloodapp.Model.Content;
import com.bloodapp.util.HomeItemAdapter;
import com.bloodapp.util.Mock;
import com.bloodapp.util.RecyclerViewClickListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";

    private DrawerLayout mDrawerLayout;

    private RecyclerView recyclerView;

    private HomeItemAdapter itemAdapter;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(HomeActivity.this, "Position " + position, Toast.LENGTH_SHORT).show();
            }
        };

        ArrayList<Content> itemList = new ArrayList<>();
        fillDummyContent(itemList);
        recyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);
        itemAdapter = new HomeItemAdapter(itemList, listener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemAdapter);


        //Definição do Toolbar (cabeçalho) e Menu Button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white);

        //referencia ao layout do drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);

        //definição do Navigation Drawer (menu lateral)
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                        Log.i(TAG, "SLIDE");
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                        Log.i(TAG, "OPEN");
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                        Log.i(TAG, "CLOSE");
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                        Log.i(TAG, "CHANGE");

                    }
                }
        );

        new Mock(HomeActivity.this).listProfilePref(TAG);

    }

    //faz o menu lateral abrir quando clicar no menu button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "on opt MENU");

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //define o que deve acontece ao clicar em cada item do menu lateral
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Log.i(TAG, "on nav MENU");
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        }
        else if (id == R.id.nav_profile) {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            Log.i(TAG, "PROFILE");
        }
        else if (id == R.id.nav_about) {
            startActivity(new Intent(HomeActivity.this, AboutActivity.class));
            Log.i(TAG, "ABOUT");
        }
        else if (id == R.id.nav_config) {
            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
            Log.i(TAG, "CONFIG");
        }
        else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            Log.i(TAG, "CONFIG");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void fillDummyContent (ArrayList<Content> itemList) {

        itemList.add(new Content("Title 1"));
        itemList.add(new Content("Title 2"));
        itemList.add(new Content("Title 3"));
        itemList.add(new Content("Title 4"));
    }
}
