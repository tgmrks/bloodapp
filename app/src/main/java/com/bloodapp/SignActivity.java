package com.bloodapp;

import android.app.AlertDialog;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bloodapp.util.Utilities;
import com.bloodapp.util.Validator;

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
    private EditText etContact;
    private EditText etBirthdate;
    private EditText etIllness;
    private CheckBox cbIllness;

    private SharedPreferences profilePref;

    private String selectedGender;
    private String selectedBloodtype;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        buttonConfirm = (Button) findViewById(R.id.buttonSignConfirm);
        buttonCancel = (Button) findViewById(R.id.buttonSignCancel);

        spnBloodType = (Spinner) findViewById(R.id.spinnerSignBloodType);
        spnGender = (Spinner) findViewById(R.id.spinnerSignGender);
        loadSpinner();

        etName = (EditText) findViewById(R.id.editSignFirstName);
        etSurename = (EditText) findViewById(R.id.editSignSurename);
        etEmail = (EditText) findViewById(R.id.editSignEmail);
        etPass = (EditText) findViewById(R.id.editSignPass);
        etContact = (EditText) findViewById(R.id.editSignContact);
        etBirthdate = (EditText) findViewById(R.id.editSignAge);
        etIllness = (EditText) findViewById(R.id.editSignWhatIllness);

        etBirthdate.setInputType(InputType.TYPE_NULL);

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
        cbIllness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked ) etIllness.setVisibility(View.VISIBLE); else etIllness.setVisibility(View.GONE);
                Log.i("CHECKBOX", "etIllness.setVisibility " + isChecked);
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fieldValidation(etEmail.getText().toString(),
                        etPass.getText().toString(),
                        etName.getText().toString(),
                        etBirthdate.getText().toString()
                )) {
                    saveProfilePref();
                    startActivity(new Intent(SignActivity.this, HomeActivity.class));
                    finish();
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
    }

    private void loadSpinner() {

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.bloodtype, android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBloodType.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGender.setAdapter(adapter2);
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
            etName.setError(getResources().getString(R.string.invalid_name));

        //Check birth date
        if(Validator.validateText(birthdate))
            validBirth = true;
        else
            etBirthdate.setError(getResources().getString(R.string.invalid_birthdate));

        return validEmail && validPass && validName && validBirth;
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
