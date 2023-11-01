package com.grouptwo.dyoga.activities;

import android.os.Bundle;

import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.enums.CourseActions;

public class EditCourseActivity extends AddCourseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // change parameters for course activity into UPDATE
        this.courseAction = CourseActions.UPDATE;
        this.screenTitle.setText(R.string.edit_course);
        this.screenDescription.setText(R.string.update_your_course_information);
    }
}