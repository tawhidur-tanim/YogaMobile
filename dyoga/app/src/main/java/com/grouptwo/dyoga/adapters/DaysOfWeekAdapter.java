package com.grouptwo.dyoga.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.grouptwo.dyoga.R;

public class DaysOfWeekAdapter extends ArrayAdapter<String> {

    private final String[] daysOfWeek;

    public DaysOfWeekAdapter(@NonNull Context context) {
        super(context, R.layout.spinner_list_item);

        this.daysOfWeek = context.getResources().getStringArray(R.array.daysOfWeek);

        this.addAll(daysOfWeek);
    }
}
