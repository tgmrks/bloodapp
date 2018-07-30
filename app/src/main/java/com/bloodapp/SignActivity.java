package com.bloodapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bloodapp.util.Utilities;
import com.bloodapp.util.Validator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class SignActivity extends AppCompatActivity {

    private static final String TAG = "SignActivity";

    private Button buttonConfirm;
    private Button buttonCancel;
    private Spinner spnBloodType;
    private Spinner spnGender;

    private EditText etName;
    private EditText etSurename;
    private EditText etEmail;
    private EditText etPass;
    private EditText etConfPass;
    private EditText etContact;
    private EditText etBirthdate;
    private EditText etIllness;
    private CheckBox cbIllness;

    private SharedPreferences profilePref;

    private String selectedGender;
    private String selectedBloodtype;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private FirebaseAuth mAuth;

    private boolean createUser = false;
    private boolean createProfile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        mAuth = FirebaseAuth.getInstance();

        buttonConfirm = (Button) findViewById(R.id.buttonSignConfirm);
        buttonCancel = (Button) findViewById(R.id.buttonSignCancel);

        spnBloodType = (Spinner) findViewById(R.id.spinnerSignBloodType);
        spnGender = (Spinner) findViewById(R.id.spinnerSignGender);
        loadSpinner();

        etName = (EditText) findViewById(R.id.editSignFirstName);
        etSurename = (EditText) findViewById(R.id.editSignSurename);
        etEmail = (EditText) findViewById(R.id.editSignEmail);
        etPass = (EditText) findViewById(R.id.editSignPass);
        etConfPass = (EditText) findViewById(R.id.editSignReenterPass);
        etContact = (EditText) findViewById(R.id.editSignContact);
        etBirthdate = (EditText) findViewById(R.id.editSignAge);
        etIllness = (EditText) findViewById(R.id.editSignWhatIllness);

        //desabilita o input do teclado para a data de nascimento
        etBirthdate.setInputType(InputType.TYPE_NULL);
        //define um calendario a ser exibido ao clicar na campo de data de nascimento
        etBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SignActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        //android.R.style.Theme_Holo_Light_Dialog,
                        //AlertDialog.THEME_TRADITIONAL,
                        //AlertDialog.THEME_HOLO_LIGHT,
                        mDateSetListener, year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //captura o que foi preenchido no calendario da data de nascimento
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month +=1 ;
                String selectedDate = day + "/" + month + "/" + year;
                Log.i("DATE", "date selected dd/mm/yyyy " + selectedDate);
                etBirthdate.setText(selectedDate);
            }
        };


        cbIllness = (CheckBox) findViewById(R.id.checkBoxSignIllness);
        //deixa campo 'doença' visivel ser o checkbox for marcado e invisivel se desmarcado
        cbIllness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked ) etIllness.setVisibility(View.VISIBLE); else etIllness.setVisibility(View.GONE);
                Log.i(TAG + " CHECKBOX", "etIllness.setVisibility " + isChecked);
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPass.getText().toString();
                String confPassword = etConfPass.getText().toString();
                String name = etName.getText().toString();
                String birthday = etBirthdate.getText().toString();
                if(fieldValidation(email, password, confPassword, name, birthday)) {
                    saveProfilePref();
                    FirebaseCreateProfile("others");
                    FirebaseCreateUser(email, password);
                }
                else
                    Toast.makeText(SignActivity.this, getResources().getString(R.string.check_valid_field), Toast.LENGTH_LONG).show();

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignActivity.this, LoginActivity.class));
                finish();
            }
        });

        //obtem qual item foi selecionado do dropdown genero
        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                   selectedGender = spnGender.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        //obtem qual item foi selecionado do dropdown tipo sanguineo
        spnBloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBloodtype = spnBloodType.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    private void FirebaseCreateUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(SignActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            String message = getResources().getString(R.string.firebase_auth_failure);
                            String exception = task.getException().getMessage().toString();

                            if (exception.equals(getResources().getString(R.string.firebase_create_failure_1)))
                                message = getResources().getString(R.string.firebase_create_mail_failure);
                            else if (exception.equals(getResources().getString(R.string.firebase_conn_failure_1)))
                                message = getResources().getString(R.string.firebase_conn_failure);
                            else
                                message = task.getException().getMessage().toString();

                            Toast.makeText(SignActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

    private boolean FirebaseCreateProfile(String others) {
        return true;
    }

    //carrega as opçoes do dropdown
    private void loadSpinner() {
        //carrega as opçoes do dropdown tipo sanguineo
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.bloodtype, android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBloodType.setAdapter(adapter1);
        //carrega as opçoes do dropdown genero
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGender.setAdapter(adapter2);
    }

    private boolean fieldValidation(String email, String pass, String confpass, String name, String birthdate) {

        boolean validEmail = false;
        boolean validPass = false;
        boolean validConfPass = false;
        boolean passCheck = false;
        boolean validName = false;
        boolean validBirth = false;

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

        //Check Password
        if(Validator.validatePassword(confpass))
            validConfPass = true;
        else
            etConfPass.setError(getResources().getString(R.string.invalid_password));

        //password confirmation
        if(validPass && validConfPass)
            if(pass.equals(confpass))
                passCheck = true;
            else {
                etConfPass.setError(getResources().getString(R.string.mismatch_password));
                etConfPass.setText("");
            }


        //Check Name
        if(Validator.validateText(name)){
            validName = true;
        }
        else
            etName.setError(getResources().getString(R.string.invalid_name));

        //Check birth date
        if(Validator.validateText(birthdate))
            validBirth = true;
        else
            etBirthdate.setError(getResources().getString(R.string.invalid_birthdate));

        return validEmail && validPass && validConfPass && passCheck && validName && validBirth;
    }

    private void saveProfilePref() {
        profilePref = getSharedPreferences(Utilities.PREF_NAME, Context.MODE_PRIVATE);
        profilePref.edit().putString(Utilities.NAME, etName.getText().toString()).commit();
        profilePref.edit().putString(Utilities.SURENAME, etSurename.getText().toString()).commit();
        profilePref.edit().putString(Utilities.EMAIL, etEmail.getText().toString()).commit();
        profilePref.edit().putString(Utilities.PASSWORD, etPass.getText().toString()).commit();
        profilePref.edit().putString(Utilities.CONTACT, etContact.getText().toString()).commit();
        profilePref.edit().putString(Utilities.GENDER, selectedGender).commit();
        profilePref.edit().putString(Utilities.BLOODTYPE, selectedBloodtype).commit();
        profilePref.edit().putString(Utilities.BIRTHDATE, etBirthdate.getText().toString()).commit();
        profilePref.edit().putString(Utilities.ILLNESS, etIllness.getText().toString()).commit();
    }
}
