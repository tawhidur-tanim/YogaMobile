package com.grouptwo.dyoga.activities;

import static com.grouptwo.dyoga.utils.Constants.COURSE;

import android.os.Bundle;
import android.view.View;

import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.components.YogaTimePicker;
import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.enums.CourseActions;

public class EditCourseActivity extends AddCourseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // change parameters for course activity into UPDATE
        this.courseAction = CourseActions.UPDATE;
        this.screenTitle.setText(R.string.edit_course);
        this.screenDescription.setText(R.string.update_your_course_information);

        final Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            final Course course = (Course) bundle.getSerializable(COURSE);
            if (course == null) return;

            courseId = course.getId();
            createdAt = course.getCreatedAt();

            int dayOfWeekPosition = daysOfWeekAdapter.getPosition(course.getDayOfWeek());
            daysOfWeekSpinner.setSelection(dayOfWeekPosition);

            int yogaTypePosition = yogaTypesAdapter.getPosition(course.getType());
            yogaTypesSpinner.setSelection(yogaTypePosition);

            int durationAdapterPosition = yogaDurationAdapter.getPosition(course.getDuration());
            durationSpinner.setSelection(durationAdapterPosition);

            priceEditText.setText(String.valueOf(course.getPrice()));
            capacityEditText.setText(String.valueOf(course.getCapacity()));

            yogaTime = course.getTime();
            timeButton.setText(YogaTimePicker.getTimeText(yogaTime));

            String description = course.getDescription();
            if (description != null && !description.isEmpty()) {
                hideDescriptionCTA();

                descriptionEditText.setText(description);
            }
        }
    }
}