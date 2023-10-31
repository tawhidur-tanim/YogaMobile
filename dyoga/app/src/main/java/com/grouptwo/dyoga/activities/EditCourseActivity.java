package com.grouptwo.dyoga.activities;

import android.os.Bundle;

import com.grouptwo.dyoga.R;

public class EditCourseActivity extends AddCourseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        screenTitle.setText(R.string.edit_course);
        screenDescription.setText(R.string.update_your_course_information);
    }
}