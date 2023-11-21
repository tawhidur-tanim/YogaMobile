package com.grouptwo.dyoga.data.entities;

import java.util.regex.Pattern;

public class EmailValidation {

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty())
            return false;

        final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

}

