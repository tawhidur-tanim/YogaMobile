package com.grouptwo.dyoga.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

    private static final String YOGA_SHARED_PREFERENCES = "YOGA_SHARED_PREFERENCES";

    public static void saveData(Context context, String key, String value) {
        final  SharedPreferences sharedPreferences = context.getSharedPreferences(YOGA_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        final  SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveData(Context context, String key, int value) {
        final  SharedPreferences sharedPreferences = context.getSharedPreferences(YOGA_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        final  SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void saveData(Context context, String key, boolean value) {
        final  SharedPreferences sharedPreferences = context.getSharedPreferences(YOGA_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        final  SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getData(Context context, String key, String defaultValue) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences(YOGA_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static int getData(Context context, String key, int defaultValue) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences(YOGA_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static Boolean getData(Context context, String key, Boolean defaultValue) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences(YOGA_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

}
