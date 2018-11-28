package com.bloodapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bloodapp.Model.Profile;

public class Mock {

    public Mock(Context context) {
        profilePref = context.getSharedPreferences(Utilities.PREF_NAME, Context.MODE_PRIVATE);
    }

    private SharedPreferences profilePref;

    public void saveProfilePref(Profile profile) {
        profilePref.edit().putString(Utilities.UID, profile.getUid()).commit();
        profilePref.edit().putString(Utilities.EMAIL, profile.getEmail()).commit();
        profilePref.edit().putString(Utilities.PASSWORD, profile.getPass()).commit();
        profilePref.edit().putString(Utilities.NAME, profile.getName()).commit();
        profilePref.edit().putString(Utilities.SURENAME, profile.getSurename()).commit();
        profilePref.edit().putString(Utilities.CONTACT, profile.getContact()).commit();
        profilePref.edit().putString(Utilities.GENDER, profile.getGender()).commit();
        profilePref.edit().putString(Utilities.BLOODTYPE, profile.getBloddType()).commit();
        profilePref.edit().putString(Utilities.BIRTHDATE, profile.getBirthdate()).commit();
        profilePref.edit().putString(Utilities.ILLNESS, profile.getIllness()).commit();
        profilePref.edit().putString(Utilities.CITY, profile.getIllness()).commit();
        profilePref.edit().putString(Utilities.STATE, profile.getIllness()).commit();
    }

    public void listProfilePref(String TAG){
        Log.i(TAG, profilePref.getString(Utilities.UID, ""));
        Log.i(TAG, profilePref.getString(Utilities.NAME, ""));
        Log.i(TAG, profilePref.getString(Utilities.SURENAME, ""));
        Log.i(TAG, profilePref.getString(Utilities.EMAIL, ""));
        Log.i(TAG, profilePref.getString(Utilities.PASSWORD, ""));
        Log.i(TAG, profilePref.getString(Utilities.CONTACT, ""));
        Log.i(TAG, profilePref.getString(Utilities.GENDER, ""));
        Log.i(TAG, profilePref.getString(Utilities.BLOODTYPE, ""));
        Log.i(TAG, profilePref.getString(Utilities.BIRTHDATE, ""));
        Log.i(TAG, profilePref.getString(Utilities.ILLNESS, ""));
    }

    public Profile loadProfilePref(){
        Profile profile = new Profile(
                profilePref.getString(Utilities.UID, ""),
                profilePref.getString(Utilities.EMAIL, ""),
                profilePref.getString(Utilities.PASSWORD, ""),
                profilePref.getString(Utilities.NAME, ""),
                profilePref.getString(Utilities.SURENAME, ""),
                profilePref.getString(Utilities.CONTACT, ""),
                profilePref.getString(Utilities.GENDER, ""),
                profilePref.getString(Utilities.BLOODTYPE, ""),
                profilePref.getString(Utilities.BIRTHDATE, ""),
                profilePref.getString(Utilities.ILLNESS, ""),
                null, null
        );
        return profile;
    }

    public void saveTokenStatus(boolean status) {
        profilePref.edit().putBoolean(Utilities.TOKEN_STATUS, status).commit();
    }

    public boolean readTokenStatus() {
        return profilePref.getBoolean(Utilities.TOKEN_STATUS, false);
    }

    public void saveNotificationChecked(String notification) {
        profilePref.edit().putString(Utilities.NOTIFICATION_CHECKED, notification).commit();
    }

    public String readNotificationChecked() {
        return profilePref.getString(Utilities.NOTIFICATION_CHECKED, "");
    }

}
