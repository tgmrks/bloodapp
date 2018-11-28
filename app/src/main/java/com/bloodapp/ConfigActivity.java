package com.bloodapp;

import android.support.annotation.NonNull;
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
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Map;

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

        swNotification = (Switch) findViewById(R.id.switch_notification);

        tokenExists();

        swNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    Log.i(TAG, "Is checked!!");
                    Toast.makeText(ConfigActivity.this, "Is checked!!", Toast.LENGTH_LONG).show();
                    subscribeToNotification(FirebaseAuth.getInstance().getUid());
                }
                else {
                    Log.i(TAG, "not checked :(");
                    Toast.makeText(ConfigActivity.this, "not checked :(", Toast.LENGTH_LONG).show();
                    unSubscribeToNotification(FirebaseAuth.getInstance().getUid());
                }


            }
        });

    }

    public void subscribeToNotification(String uid){
        userToken = new Token(uid, FirebaseInstanceId.getInstance().getToken());
        Log.i(TAG, "Refreshed uid: " + userToken.getUid());
        Log.i(TAG, "Refreshed token: " + userToken.getToken());
        //mDatabase.child(Utilities.TOKENS).push().setValue(token);
        mDatabase.child(Utilities.TOKENS).child(uid).setValue(userToken);
    }

    public void unSubscribeToNotification(String uid){
        mDatabase.child(Utilities.TOKENS).child(uid).removeValue();
        /* mDatabase.child(Utilities.TOKENS).orderByChild("uid").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { ... }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { ... }
        }); */
    }

    public void tokenExists() {
        boolean swNotificationStatus = new Mock(ConfigActivity.this).readTokenStatus();
        if(swNotificationStatus)
            swNotification.setChecked(true);
        else
            swNotification.setChecked(false);

        /*
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + value);
                //Log.d(TAG, "Object is: " + object);

                if(value != null)
                    swNotification.setChecked(true);
                else
                    swNotification.setChecked(false);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/
    }
}
