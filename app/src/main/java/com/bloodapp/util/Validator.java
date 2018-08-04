package com.bloodapp.util;

import android.text.TextUtils;
import android.util.Log;

public class Validator {

    public final static boolean validateEmail(String txtEmail) {
        if (TextUtils.isEmpty(txtEmail)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches();
        }
    }

    public final static boolean validatePassword(String txtPass) {
        if (TextUtils.isEmpty(txtPass)) {
            return false;
        } else {
            return txtPass.length() >= 6;
        }
    }

    public final static boolean validateText(String txt) {
        return !TextUtils.isEmpty(txt);
    }

    public final static boolean isSameText(String label, String txtOne, String txtTwo) {
        if (txtOne.equals(txtTwo)) {
            Log.i(label, "TRUE - One: " + txtOne + " - Two: " + txtTwo);
            return true;
        } else {
            Log.i(label, "FALSE - One: " + txtOne + " - Two: " + txtTwo);
            return false;
        }
    }
}
