package com.grouptwo.dyoga.activities;

import static com.grouptwo.dyoga.components.CourseBottomSheet.DURATION_TEXT;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.adapters.DaysOfWeekAdapter;
import com.grouptwo.dyoga.adapters.YogaDurationAdapter;
import com.grouptwo.dyoga.adapters.YogaTypesAdapter;
import com.grouptwo.dyoga.components.CourseBottomSheet;
import com.grouptwo.dyoga.components.YogaTimePicker;
import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.data.repositories.CourseRepository;
import com.grouptwo.dyoga.enums.CourseActions;
import com.grouptwo.dyoga.interfaces.Callback;
import com.grouptwo.dyoga.utils.DoubleConverter;

import java.time.LocalDateTime;
import java.util.HashMap;

public class AddCourseActivity extends AppCompatActivity implements CourseBottomSheet.BottomSheetListener, Callback<Boolean> {

    protected Spinner yogaTypesSpinner, daysOfWeekSpinner, durationSpinner;
    protected Button timeButton, reviewButton;
    protected ImageView addDescriptionImageView;
    protected TextView screenTitle, screenDescription, addDescriptionTextView;
    protected LinearLayout descriptionDisplay;
    protected EditText priceEditText, capacityEditText, descriptionEditText;
    protected CourseActions courseAction;

    private LocalDateTime yogaTime;
    private CourseRepository courseRepository;

    // view adapters
    private YogaTypesAdapter yogaTypesAdapter;
    private DaysOfWeekAdapter daysOfWeekAdapter;
    private YogaDurationAdapter yogaDurationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_course);

        yogaTime = LocalDateTime.now();
        courseAction = CourseActions.NEW;
        courseRepository = new CourseRepository(this);

        yogaTypesAdapter = new YogaTypesAdapter(this);
        daysOfWeekAdapter = new DaysOfWeekAdapter(this);
        yogaDurationAdapter = new YogaDurationAdapter(this);

        yogaTypesSpinner = findViewById(R.id.yogaTypesSpinner);
        daysOfWeekSpinner = findViewById(R.id.daysOfWeekSpinner);
        durationSpinner = findViewById(R.id.durationSpinner);

        timeButton = findViewById(R.id.timeButton);
        reviewButton = findViewById(R.id.reviewButton);

        screenTitle = findViewById(R.id.screenTitle);
        screenDescription = findViewById(R.id.screenDescription);
        addDescriptionTextView = findViewById(R.id.addDescriptionTextView);
        addDescriptionImageView = findViewById(R.id.addDescriptionImageView);
        descriptionDisplay = findViewById(R.id.descriptionDisplay);

        priceEditText = findViewById(R.id.priceEditText);
        capacityEditText = findViewById(R.id.capacityEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);

        // init view
        setViewItems();
        setButtonListeners();
    }

    private void setViewItems() {
        yogaTypesSpinner.setAdapter(yogaTypesAdapter);
        daysOfWeekSpinner.setAdapter(daysOfWeekAdapter);
        durationSpinner.setAdapter(yogaDurationAdapter);

        timeButton.setText(YogaTimePicker.getCurrentTimeText());
    }

    private void setButtonListeners() {
        final View.OnClickListener descriptionClickListener = v -> {
            descriptionDisplay.setVisibility(View.VISIBLE);
            addDescriptionTextView.setVisibility(View.GONE);
            addDescriptionImageView.setVisibility(View.GONE);

            descriptionEditText.requestFocus();
        };

        addDescriptionTextView.setOnClickListener(descriptionClickListener);
        addDescriptionImageView.setOnClickListener(descriptionClickListener);

        timeButton.setOnClickListener(v -> YogaTimePicker.show(yogaTime, yogaTimePicker -> {
            timeButton.setText(yogaTimePicker.getTimeText());
            yogaTime = yogaTimePicker.getLocalDateTime();
        }, getSupportFragmentManager()));

        reviewButton.setOnClickListener(v -> {
            if (isInputsValid()) {
                CourseBottomSheet.show(getCourseData(), getSupportFragmentManager());
            }
        });
    }

    private boolean isInputsValid() {
        final String priceText = priceEditText.getText().toString();
        final String capacityText = capacityEditText.getText().toString();

        if (priceText.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.course_price_is_required, Toast.LENGTH_LONG).show();
            return false;
        } else if (DoubleConverter.convert(priceText) <= 0) {
            Toast.makeText(getApplicationContext(), R.string.enter_a_valid_course_price, Toast.LENGTH_LONG).show();
            return false;
        }

        if (capacityText.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.course_capacity_is_required, Toast.LENGTH_LONG).show();
            return false;
        } else if (DoubleConverter.convert(capacityText) <= 0) {
            Toast.makeText(getApplicationContext(), R.string.enter_a_valid_course_capacity, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    protected CourseBottomSheet.CourseData getCourseData() {
        final String yogaType = yogaTypesSpinner.getSelectedItem().toString();
        final double price = DoubleConverter.convert(priceEditText.getText().toString());
        final int capacity = Integer.parseInt(capacityEditText.getText().toString());
        final String description = descriptionEditText.getText().toString();
        final String dayOfWeek = daysOfWeekSpinner.getSelectedItem().toString();
        final int duration = yogaDurationAdapter.getDurationValues(durationSpinner.getSelectedItemPosition());
        final Course course =  new Course(yogaType, yogaTime, price, capacity, duration, description, dayOfWeek);

        final HashMap<String, String> extras = new HashMap<>();
        extras.put(DURATION_TEXT, durationSpinner.getSelectedItem().toString());

        return new CourseBottomSheet.CourseData(course, extras);
    }

    @Override
    public void onButtonClicked(CourseBottomSheet.CourseData courseData) {
        Course course = courseData.getCourse();
        if (courseAction == CourseActions.NEW) {
            courseRepository.save(course, this);
        } else {
            courseRepository.update(1, course, this);
        }
    }

    @Override
    public void onResult(Boolean value) {
        if (value != null && value) {
            // show success message
            Toast.makeText(getApplicationContext(),
                    R.string.course_has_been_added,
                    Toast.LENGTH_LONG
            ).show();

            // close the current activity and return to source activity
            finish();
        } else {
            // show an error message, unable to save course
            Toast.makeText(getApplicationContext(),
                    R.string.an_error_occurred_saving_course_please_try_again,
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}