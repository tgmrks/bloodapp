package com.bloodapp.util;

import android.text.TextUtils;

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
            return txtPass.length() >= 4;
        }
    }

    public final static boolean validateText(String txt) {
        return !TextUtils.isEmpty(txt);
    }
}
