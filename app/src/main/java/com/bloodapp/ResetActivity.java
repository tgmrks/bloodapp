package com.bloodapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bloodapp.util.Validator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {

    private static final String TAG = "ResetActivity";

    private EditText etEmail;
    private Button btSendReset;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        etEmail = (EditText) findViewById(R.id.editResetEmail);
        btSendReset = (Button) findViewById(R.id.buttonPassReset);

        mAuth = FirebaseAuth.getInstance();

        btSendReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                if(fieldValidation(email))
                    CallPasswordReset(email);
            }
        });

    }

    private void CallPasswordReset(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(ResetActivity.this, getResources().getString(R.string.reset_pass_message), Toast.LENGTH_LONG).show();
                            etEmail.setText("");
                        }
                        else {
                            Log.w(TAG, task.getException());
                            String message = getResources().getString(R.string.firebase_auth_failure);
                            String exception = task.getException().getMessage().toString();

                            if (exception.equals(getResources().getString(R.string.firebase_reset_failure_1)))
                                message = getResources().getString(R.string.firebase_reset_mail_no_record_failure);
                            else if (exception.equals(getResources().getString(R.string.firebase_reset_failure_2)))
                                message = getResources().getString(R.string.firebase_reset_mail_bad_formatted_failure);
                            else if (exception.equals(getResources().getString(R.string.firebase_conn_failure_1)))
                                message = getResources().getString(R.string.firebase_conn_failure);
                            else
                                message = task.getException().getMessage().toString();

                            Toast.makeText(ResetActivity.this, message, Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    private boolean fieldValidation(String email) {

        boolean validEmail = false;

        //Check Email
        if(Validator.validateEmail(email))
            validEmail = true;
        else
            etEmail.setError(getResources().getString(R.string.invalid_email));

        return validEmail;
    }
}
