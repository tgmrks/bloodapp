package com.bloodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bloodapp.util.Utilities;
import com.bloodapp.util.Validator;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    //cria variaveis
    //nivel de acesso / tipo / nome
    private TextView tvResetPass;
    private EditText etEmail;
    private EditText etPass;
    private Button login;
    private Button sign;

    //o SharedPreferences será usado para gravar os dados temporariamente
    private SharedPreferences profilePref;

    private FirebaseAuth mAuth;

    //onCreate é o que cria toda a Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //aponta para o XML que representa esta activity
        setContentView(R.layout.activity_login);

        tvResetPass = (TextView) findViewById(R.id.textViewResetPass);

        //faz referencia para cada component do XML
        etEmail = (EditText) findViewById(R.id.editLoginEmail);
        etPass = (EditText) findViewById(R.id.editLoginPass);

        login = (Button) findViewById(R.id.buttonLogin);
        sign = (Button) findViewById(R.id.buttonSign);

        profilePref = getSharedPreferences(Utilities.PREF_NAME, Context.MODE_PRIVATE);

        //Autenticacao com Firebase
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //setOnClickListener é o que vai "ouvir" a interação do usuário com um componente, neste caso o botão login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String passowrd = etPass.getText().toString();
                //dentro do setOnClickListener vc programa o que quer que aconteça quando o usuário clicar no botão/componente
                //  if(fieldValidation(email, passowrd)) {
                if(fieldValidation(email, passowrd)) {
                    FirebaseSignIn(email, passowrd);
                }
                else {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.check_valid_field), Toast.LENGTH_LONG).show();
                }


            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(LoginActivity.this, SignActivity.class));
            }
        });

        tvResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetActivity.class));
            }
        });

        }

    private boolean FirebaseSignIn(String email,String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d( TAG + " Sign In", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG + " Sign In", "signInWithEmail:failure", task.getException());
                            String message = getResources().getString(R.string.firebase_auth_failure);
                            String exception = task.getException().getMessage().toString();

                            if (exception.equals(getResources().getString(R.string.firebase_auth_failure_1)))
                                message = getResources().getString(R.string.firebase_pass_failure);
                            else if (exception.equals(getResources().getString(R.string.firebase_auth_failure_2)))
                                message = getResources().getString(R.string.firebase_mail_failure);
                            else if (exception.equals(getResources().getString(R.string.firebase_conn_failure_1)))
                                message = getResources().getString(R.string.firebase_conn_failure);
                            else
                                message = task.getException().getMessage().toString();

                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                        // ...
                    }
                });

        return false;
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

     /*   String localEmail = profilePref.getString(Utilities.EMAIL, "");
        String localPass = profilePref.getString(Utilities.PASSWORD, "");
        //Check Login exists
        if(!localEmail.equals("") && !localPass.equals("")){
        }
        else {
            etEmail.setError(getResources().getString(R.string.invalid_email));
            etPass.setError(getResources().getString(R.string.invalid_password));
    } */

        return validEmail && validPass;
    }

    //se ja estiver conectado, pula para tela Home
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Log.i(TAG + " User", currentUser.getEmail().toString());
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
    }
}
