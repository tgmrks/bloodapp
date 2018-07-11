package com.bloodapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bloodapp.Model.Profile;
import com.bloodapp.util.Utilities;
import com.bloodapp.util.Validator;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private EditText etFirstname;
    private EditText etSurename;
    private EditText etEmail;
    private EditText etPass;
    private EditText etContact;
    private EditText etBirthdate;
    private EditText etIllness;

    private Spinner spnGender;
    private Spinner spnBloodType;

    private Button btnCancel;
    private Button btnConfirm;

    private SharedPreferences profilePref;

    private String selectedGender;
    private String selectedBloodtype;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etFirstname = (EditText) findViewById(R.id.profile_edit_name);
        etSurename = (EditText) findViewById(R.id.profile_edit_surename);
        etEmail = (EditText) findViewById(R.id.profile_edit_email);
        etPass = (EditText) findViewById(R.id.profile_edit_pass);
        etContact = (EditText) findViewById(R.id.profile_edit_contact);
        etBirthdate = (EditText) findViewById(R.id.profile_edit_birthdate);
        etIllness = (EditText) findViewById(R.id.profile_edit_illness);

        spnBloodType = (Spinner) findViewById(R.id.profile_spinner_bloodtype);
        spnGender = (Spinner) findViewById(R.id.profile_spinner_gender);

        btnCancel = (Button) findViewById(R.id.profile_button_cancel);
        btnConfirm = (Button) findViewById(R.id.profile_button_confirm);

        etEmail.setKeyListener(null);
        etPass.setKeyListener(null);

        loadProfilePref();


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fieldValidation(etEmail.getText().toString(),
                        etPass.getText().toString(),
                        etFirstname.getText().toString(),
                        etBirthdate.getText().toString()
                )) {
                    saveProfilePref();
                    startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                    finish();
                }
                else
                    Toast.makeText(ProfileActivity.this, getResources().getString(R.string.check_valid_field), Toast.LENGTH_LONG).show();

            }
        });

        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedGender = spnGender.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        spnBloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBloodtype = spnBloodType.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });


        etBirthdate.setInputType(InputType.TYPE_NULL);

        etBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ProfileActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        //android.R.style.Theme_Holo_Light_Dialog,
                        //AlertDialog.THEME_TRADITIONAL,
                        //AlertDialog.THEME_HOLO_LIGHT,
                        mDateSetListener, year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month +=1 ;
                String selectedDate = day + "/" + month + "/" + year;
                Log.i(TAG + " DATE", "date selected dd/mm/yyyy " + selectedDate);
                etBirthdate.setText(selectedDate);
            }
        };


    }

    private void loadProfilePref(){
        profilePref = getSharedPreferences(Utilities.PREF_NAME, Context.MODE_PRIVATE);
        etEmail.setText(profilePref.getString(Utilities.EMAIL, ""));
        etPass.setText(profilePref.getString(Utilities.PASSWORD, ""));
        etFirstname.setText(profilePref.getString(Utilities.NAME, ""));
        etSurename.setText(profilePref.getString(Utilities.SURENAME, ""));
        etContact.setText(profilePref.getString(Utilities.CONTACT, ""));
        etBirthdate.setText(profilePref.getString(Utilities.BIRTHDATE, ""));
        etIllness.setText(profilePref.getString(Utilities.ILLNESS, ""));

        String gender = profilePref.getString(Utilities.GENDER, "");
        String bloodType = profilePref.getString(Utilities.BLOODTYPE, "");

        loadSpinner(gender, bloodType);
    }

    private void loadSpinner(String gender, String bloodType) {

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.bloodtype, android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBloodType.setAdapter(adapter1);

        if (!bloodType.equals("")) {
            int spinnerPostion = adapter1.getPosition(gender);
            spnBloodType.setSelection(spinnerPostion);
            spinnerPostion = 0;
        }

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGender.setAdapter(adapter2);

        if (!gender.equals("")) {
            int spinnerPostion = adapter2.getPosition(gender);
            spnGender.setSelection(spinnerPostion);
            spinnerPostion = 0;
        }
    }

    private boolean fieldValidation(String email, String pass, String name, String birthdate) {

        boolean validEmail = false;
        boolean validPass = false;
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

        //Check Name
        if(Validator.validateText(name)){
            validName = true;
        }
        else
            etFirstname.setError(getResources().getString(R.string.invalid_name));

        //Check birth date
        if(Validator.validateText(birthdate))
            validBirth = true;
        else
            etBirthdate.setError(getResources().getString(R.string.invalid_birthdate));

        return validEmail && validPass && validName && validBirth;
    }

    private void saveProfilePref() {
        profilePref = getSharedPreferences(Utilities.PREF_NAME, Context.MODE_PRIVATE);
        profilePref.edit().putString(Utilities.NAME, etFirstname.getText().toString()).commit();
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
