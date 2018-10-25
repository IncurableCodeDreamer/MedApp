package com.example.klaudia.medicalcenter;

import android.app.Application;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator extends Application {

    public boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }

    public boolean isValidNotEmpty(String string) {
        if (string != null && string.length() > 3) {
            return true;
        }
        return false;
    }

    public void setError(String type, EditText editText) {
        editText.setError("Invalid " + type);
    }

    public static void clearError(EditText editText) {
        editText.setError(null);
    }

}
