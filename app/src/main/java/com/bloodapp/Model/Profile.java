package com.bloodapp.Model;

import android.content.Context;
import android.util.Log;

import com.bloodapp.util.Mock;
import com.bloodapp.util.Utilities;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Profile {

    private String uid;
    private String email;
    private String pass;
    private String name;
    private String surename;
    private String contact;
    private String gender;
    private String bloddType;
    private String birthdate;
    private String illness;
    private String state;
    private String city;

    private DatabaseReference mDatabase;

    public Profile() {
    }

    public Profile(String uid, String email, String pass) {
        this.uid = uid;
        this.email = email;
        this.pass = pass;
    }

    public Profile(String uid, String email, String pass, String name, String surename, String contact, String gender, String bloddType, String birthdate, String illness, String state, String city) {
        this.uid = uid;
        this.email = email;
        this.pass = pass;
        this.name = name;
        this.surename = surename;
        this.contact = contact;
        this.gender = gender;
        this.bloddType = bloddType;
        this.birthdate = birthdate;
        this.illness = illness;
        this.state = state;
        this.city = city;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloddType() {
        return bloddType;
    }

    public void setBloddType(String bloddType) {
        this.bloddType = bloddType;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void saveFirebaseRD(String uid, Context context){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setUid(uid);
        Mock mock = new Mock(context);
        mock.saveProfilePref(this);
        setPass(null); setUid(null);
        mDatabase.child(Utilities.PROFILES).child(uid).setValue(this);
    }

    public void readFirebaseRD(final String uid, final Context context, final String TAG){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(Utilities.PROFILES).orderByChild(uid);

        //Mock mock = new Mock(context);
        //mock.saveProfilePref(this);
        //setPass(null); setUid(null);

        Log.d(TAG, "uid is: " + uid);
        // Read from the database

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + value);
                //Log.d(TAG, "Object is: " + object);


                try { setName(dataSnapshot.child(Utilities.PROFILES).child(uid).child(Utilities.name_FIELD).getValue().toString()); } catch (Exception e) { Log.d(Utilities.name_FIELD, "FAILED"); }
                try { setSurename(dataSnapshot.child(Utilities.PROFILES).child(uid).child(Utilities.surename_FIELD).getValue().toString()); } catch (Exception e) { Log.d(Utilities.surename_FIELD, "FAILED"); }
                try { setContact(dataSnapshot.child(Utilities.PROFILES).child(uid).child(Utilities.contact_FIELD).getValue().toString()); } catch (Exception e) { Log.d(Utilities.contact_FIELD, "FAILED"); }
                try { setGender(dataSnapshot.child(Utilities.PROFILES).child(uid).child(Utilities.gender_FIELD).getValue().toString()); } catch (Exception e) { Log.d(Utilities.gender_FIELD, "FAILED"); }
                try { setBloddType(dataSnapshot.child(Utilities.PROFILES).child(uid).child(Utilities.bloddType_FIELD).getValue().toString()); } catch (Exception e) { Log.d(Utilities.bloddType_FIELD, "FAILED"); }
                try { setBirthdate(dataSnapshot.child(Utilities.PROFILES).child(uid).child(Utilities.birthdate_FIELD).getValue().toString()); } catch (Exception e) { Log.d(Utilities.birthdate_FIELD, "FAILED"); }
                try { setIllness(dataSnapshot.child(Utilities.PROFILES).child(uid).child(Utilities.illness_FIELD).getValue().toString()); } catch (Exception e) { Log.d(Utilities.illness_FIELD, "FAILED"); }

                Mock mock = new Mock(context);
                mock.saveProfilePref(Profile.this);

                 print();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    public void print(){
        Log.d("UIA", getUid());
        Log.d("EMAIL", getEmail());
        Log.d("PASS", getPass());
        Log.d("NAME", getName());
        Log.d("SURE", getSurename());
        Log.d("CONTACT", getContact());
        Log.d("GENDER", getGender());
        Log.d("BLOOD", getBloddType());
        Log.d("BIRTH", getBirthdate());
        Log.d("ILLNESS", getIllness());
    }


}
