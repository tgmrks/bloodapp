package com.bloodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.bloodapp.util.Utilities;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences profilePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //clearProfilePref();

        Thread splash_screen = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                }
            }
        };
        splash_screen.start();
    }

    private void clearProfilePref() {
        profilePref = getSharedPreferences(Utilities.PREF_NAME, Context.MODE_PRIVATE);
        profilePref.edit().putString(Utilities.NAME, "").commit();
        profilePref.edit().putString(Utilities.SURENAME, "").commit();
        profilePref.edit().putString(Utilities.EMAIL, "").commit();
        profilePref.edit().putString(Utilities.PASSWORD, "").commit();
        profilePref.edit().putString(Utilities.CONTACT, "").commit();
        profilePref.edit().putString(Utilities.GENDER, "").commit();
        profilePref.edit().putString(Utilities.BLOODTYPE, "").commit();
        profilePref.edit().putString(Utilities.BIRTHDATE, "").commit();
        profilePref.edit().putString(Utilities.ILLNESS, "").commit();
    }
}
