package com.grouptwo.dyoga.activities;

import static com.grouptwo.dyoga.components.CourseBottomSheet.DURATION_TEXT;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.adapters.DaysOfWeekAdapter;
import com.grouptwo.dyoga.adapters.YogaDurationAdapter;
import com.grouptwo.dyoga.adapters.YogaTypesAdapter;
import com.grouptwo.dyoga.components.CourseBottomSheet;
import com.grouptwo.dyoga.components.YogaTimePicker;
import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.data.viewmodels.CourseViewModel;
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

    protected long courseId;
    protected LocalDateTime yogaTime, createdAt;

    // view adapters
    protected YogaTypesAdapter yogaTypesAdapter;
    protected DaysOfWeekAdapter daysOfWeekAdapter;
    protected YogaDurationAdapter yogaDurationAdapter;

    private Handler handler;
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_course);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        yogaTime = LocalDateTime.now();
        createdAt = LocalDateTime.now();
        courseAction = CourseActions.NEW;
        handler = new Handler(Looper.getMainLooper());

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
            hideDescriptionCTA();

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

    protected void hideDescriptionCTA() {
        descriptionDisplay.setVisibility(View.VISIBLE);
        addDescriptionTextView.setVisibility(View.GONE);
        addDescriptionImageView.setVisibility(View.GONE);
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
        final int duration = yogaDurationAdapter.getDurationValue(durationSpinner.getSelectedItemPosition());
        final Course course =  new Course(yogaType, yogaTime, price, capacity, duration, description, dayOfWeek);
        course.setCreatedAt(createdAt);

        final HashMap<String, String> extras = new HashMap<>();
        extras.put(DURATION_TEXT, durationSpinner.getSelectedItem().toString());

        return new CourseBottomSheet.CourseData(course, extras);
    }

    @Override
    public void onButtonClicked(CourseBottomSheet.CourseData courseData) {
        Course course = courseData.getCourse();
        if (courseAction == CourseActions.NEW) {
            courseViewModel.save(course, this);
        } else {
            courseViewModel.update(courseId, course, this);
        }
    }

    @Override
    public void onResult(Boolean value) {
        if (value != null && value) {
            // show success message
            if (courseAction == CourseActions.NEW) {
                showToast(R.string.course_has_been_added);
            } else {
                showToast(R.string.course_has_been_updated);
            }

            // close the current activity and return to source activity
            finish();
        } else {
            // show an error message, unable to save course
            showToast(R.string.an_error_occurred_saving_course_please_try_again);
        }
    }

    private void showToast(int message) {
        handler.post(() -> Toast.makeText(getApplicationContext(),
                message, Toast.LENGTH_LONG).show());
    }
}