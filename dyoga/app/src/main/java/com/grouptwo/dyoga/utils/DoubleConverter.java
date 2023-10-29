package com.grouptwo.dyoga.utils;

import android.util.Log;

public class DoubleConverter {

    public static double convert(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            Log.e(DoubleConverter.class.getName(), "Exception thrown converting " + value + " to double");
        }
        return 0;
    }
}
