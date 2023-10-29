package com.grouptwo.dyoga.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.grouptwo.dyoga.R;

public class YogaDurationAdapter extends ArrayAdapter<String> {

    private final int[] durationValues = {
            10, 20, 30, 40, 50, 60, 2, 4
    };

    private final String[] durations;

    public YogaDurationAdapter(@NonNull Context context) {
        super(context, R.layout.spinner_list_item);

        this.durations = context.getResources().getStringArray(R.array.durations);

        this.addAll(durations);
    }

    public String[] getDurations() {
        return durations;
    }

    public int[] getDurationValues() {
        return durationValues;
    }
}
