package com.bloodapp.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public void saveFirebaseRD(String uid){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setPass(null); setUid(null);
        mDatabase.child("profiles").child(uid).setValue(this);
    }


}
