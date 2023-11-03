package com.grouptwo.dyoga.activities;

import static com.grouptwo.dyoga.utils.Constants.CLASS_INSTANCE;
import static com.grouptwo.dyoga.utils.Constants.COURSE;

import android.os.Bundle;
import android.view.View;

import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.components.YogaDatePicker;
import com.grouptwo.dyoga.components.YogaTimePicker;
import com.grouptwo.dyoga.data.entities.ClassInstance;
import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.enums.ClassInstanceActions;
import com.grouptwo.dyoga.enums.CourseActions;

public class EditClassInstanceActivity extends AddClassInstanceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.classInstanceAction = ClassInstanceActions.UPDATE;
        this.screenTitle.setText(R.string.edit_class);

        final Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            course = (Course) bundle.getSerializable(COURSE);
            final ClassInstance classInstance = (ClassInstance) bundle.getSerializable(CLASS_INSTANCE);

            if (course == null || classInstance == null) return;

            this.screenDescription.setText(String.format(getApplicationContext().getString(R.string.s_a_new_class_instance_to_family_yoga_by_time)
                    ,"Update",course.getTimeText()));

            classId = classInstance.getId();
            createdAt = course.getCreatedAt();

            instrcutorEditText.setText(String.valueOf(classInstance.getInstructor()));
            roomEditText.setText(String.valueOf(classInstance.getRoom()));

            yogaDate = classInstance.getDate();
            dateButton.setText(YogaDatePicker.getDateText(yogaDate));
            saveButton.setText(R.string.update_button);


            String description = classInstance.getComments();
            if (description != null && !description.isEmpty()) {
                hideDescriptionCTA();
                descriptionEditText.setText(description);
            }


            // show delete icon
            deleteButton.setVisibility(View.VISIBLE);
        }
    }
}