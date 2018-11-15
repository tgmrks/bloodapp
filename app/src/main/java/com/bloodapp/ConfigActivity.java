package com.bloodapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.bloodapp.Model.Profile;
import com.bloodapp.Model.Token;
import com.bloodapp.util.Mock;
import com.bloodapp.util.Utilities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class ConfigActivity extends AppCompatActivity {

    private static final String TAG = "ConfigActivity";

    private Switch swNotification;

    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;

    private Token userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        userToken = new Token(FirebaseAuth.getInstance().getUid(), FirebaseInstanceId.getInstance().getToken());
        Log.i(TAG, "Refreshed uid: " + userToken.getUid());
        Log.i(TAG, "Refreshed token: " + userToken.getToken());


        swNotification = (Switch) findViewById(R.id.switch_notification);

        swNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    Log.i(TAG, "Is checked!!");
                    Toast.makeText(ConfigActivity.this, "Is checked!!", Toast.LENGTH_LONG).show();
                    subscribeToNotification(userToken);
                }
                else {
                    Log.i(TAG, "not checked :(");
                    Toast.makeText(ConfigActivity.this, "not checked :(", Toast.LENGTH_LONG).show();
                    unSubscribeToNotification(userToken);
                }


            }
        });

    }

    public void subscribeToNotification(Token token){
        mDatabase.child(Utilities.TOKENS).push().setValue(token);
    }

    public void unSubscribeToNotification(Token token){
        //mDatabase.child(Utilities.TOKENS).removeValue(token);
    }
}
