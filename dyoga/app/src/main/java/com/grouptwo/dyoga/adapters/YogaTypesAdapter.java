package com.grouptwo.dyoga.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.grouptwo.dyoga.R;

public class YogaTypesAdapter extends ArrayAdapter<String> {

    private final String[] yogaTypes;

    public YogaTypesAdapter(@NonNull Context context) {
        super(context, R.layout.spinner_list_item);

        this.yogaTypes = context.getResources().getStringArray(R.array.yogaTypes);

        this.addAll(yogaTypes);
    }

    public String[] getYogaTypes() {
        return yogaTypes;
    }
}
