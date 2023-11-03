package com.grouptwo.dyoga.activities;

import static com.grouptwo.dyoga.utils.Constants.COURSE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.adapters.DaysOfWeekAdapter;
import com.grouptwo.dyoga.adapters.YogaDurationAdapter;
import com.grouptwo.dyoga.adapters.YogaTypesAdapter;
import com.grouptwo.dyoga.components.CourseBottomSheet;
import com.grouptwo.dyoga.components.YogaTimePicker;
import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.data.viewmodels.ClassInstanceViewModel;
import com.grouptwo.dyoga.data.viewmodels.CourseViewModel;
import com.grouptwo.dyoga.enums.ClassInstanceActions;
import com.grouptwo.dyoga.enums.CourseActions;
import com.grouptwo.dyoga.utils.DoubleConverter;

import java.time.LocalDateTime;

public class AddClassInstanceActivity extends AppCompatActivity {

    protected Button timeButton, saveButton;
    protected ImageView addDescriptionImageView;
    protected TextView screenTitle, screenDescription, addDescriptionTextView;
    protected LinearLayout descriptionDisplay;
    protected EditText instrcutorEditText, roomEditText, descriptionEditText;
    protected ClassInstanceActions classInstanceAction;

    protected int classId;

    private Handler handler;
    private ClassInstanceViewModel classInstanceViewModel;

    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_class_instance);

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            course = (Course) bundle.getSerializable(COURSE);
        }


        classInstanceViewModel = new ViewModelProvider(this).get(ClassInstanceViewModel.class);

        handler = new Handler(Looper.getMainLooper());
        classInstanceAction = ClassInstanceActions.NEW;

        saveButton = findViewById(R.id.saveClassButton);

        screenTitle = findViewById(R.id.clasScreenHeading);
        screenDescription = findViewById(R.id.screenDescription);
        addDescriptionTextView = findViewById(R.id.classAddDescriptionTextView);
        addDescriptionImageView = findViewById(R.id.classAddDescriptionImageView);
        descriptionDisplay = findViewById(R.id.classDescriptionDisplay);

        descriptionEditText = findViewById(R.id.classDescriptionEditText);
        instrcutorEditText = findViewById(R.id.classInstructorEditText);
        roomEditText = findViewById(R.id.roomEditText);

        setButtonListeners();
    }


    private void setButtonListeners() {
        final View.OnClickListener descriptionClickListener = v -> {
            hideDescriptionCTA();
            descriptionEditText.requestFocus();
        };

        addDescriptionTextView.setOnClickListener(descriptionClickListener);
        addDescriptionImageView.setOnClickListener(descriptionClickListener);

        saveButton.setOnClickListener(v -> {
            if(!isInputsValid()){
                return;
            }

            Toast.makeText(this, "Valid input", Toast.LENGTH_SHORT).show();

        });
    }

    private boolean isInputsValid() {
        final String instructorText = instrcutorEditText.getText().toString();
        final String roomText = roomEditText.getText().toString();

        if (instructorText.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.instructor_is_required, Toast.LENGTH_LONG).show();
            return false;
        }

        if (roomText.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.room_number_is_required, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    protected void hideDescriptionCTA() {
        descriptionDisplay.setVisibility(View.VISIBLE);
        addDescriptionTextView.setVisibility(View.GONE);
        addDescriptionImageView.setVisibility(View.GONE);
    }
}