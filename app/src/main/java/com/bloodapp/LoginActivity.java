package com.bloodapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bloodapp.util.Validator;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPass;
    private Button login;
    private Button sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.editLoginEmail);
        etPass = (EditText) findViewById(R.id.editLoginPass);

        login = (Button) findViewById(R.id.buttonLogin);
        sign = (Button) findViewById(R.id.buttonSign);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fieldValidation(etEmail.getText().toString(), etPass.getText().toString())) {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }
                else
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.check_valid_field), Toast.LENGTH_LONG).show();
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(LoginActivity.this, SignActivity.class));
                    finish();
            }
        });

    }

    private boolean fieldValidation(String email, String pass) {

        boolean validEmail = false;
        boolean validPass = false;

        //Check Email
        if(Validator.validateEmail(email))
            validEmail = true;
        else
            etEmail.setError(getResources().getString(R.string.invalid_email));

        //Check Password
        if(Validator.validatePassword(pass))
            validPass = true;
        else
            etPass.setError(getResources().getString(R.string.invalid_password));

        return validEmail && validPass;
    }
}
