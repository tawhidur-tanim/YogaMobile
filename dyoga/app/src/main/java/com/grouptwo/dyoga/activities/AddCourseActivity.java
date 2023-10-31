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
import com.grouptwo.dyoga.utils.DoubleConverter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AddCourseActivity extends AppCompatActivity implements CourseBottomSheet.BottomSheetListener {

    // view adapters
    private YogaTypesAdapter yogaTypesAdapter;
    private DaysOfWeekAdapter daysOfWeekAdapter;
    private YogaDurationAdapter yogaDurationAdapter;

    private Spinner yogaTypesSpinner, daysOfWeekSpinner, durationSpinner;
    private Button timeButton, reviewButton;
    private ImageView addDescriptionImageView;
    private TextView addDescriptionTextView;
    private LinearLayout descriptionDisplay;
    private EditText priceEditText, capacityEditText, descriptionEditText;

    private LocalDateTime yogaTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        yogaTime = LocalDateTime.now();

        yogaTypesAdapter = new YogaTypesAdapter(this);
        daysOfWeekAdapter = new DaysOfWeekAdapter(this);
        yogaDurationAdapter = new YogaDurationAdapter(this);

        yogaTypesSpinner = findViewById(R.id.yogaTypesSpinner);
        daysOfWeekSpinner = findViewById(R.id.daysOfWeekSpinner);
        durationSpinner = findViewById(R.id.durationSpinner);

        timeButton = findViewById(R.id.timeButton);
        reviewButton = findViewById(R.id.reviewButton);

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

    private CourseBottomSheet.CourseData getCourseData() {
        final String yogaType = yogaTypesSpinner.getSelectedItem().toString();
        final double price = DoubleConverter.convert(priceEditText.getText().toString());
        final int capacity = Integer.parseInt(capacityEditText.getText().toString());
        final String description = descriptionEditText.getText().toString();
        final String dayOfWeek = daysOfWeekSpinner.getSelectedItem().toString();
        final int duration = yogaDurationAdapter.getDurationValues(durationSpinner.getSelectedItemPosition());

        final Course course = new Course(yogaType, yogaTime, price, capacity, duration, description, dayOfWeek);
        final HashMap<String, String> extras = new HashMap<>();
        extras.put(DURATION_TEXT, durationSpinner.getSelectedItem().toString());

        return new CourseBottomSheet.CourseData(course, extras);
    }

    @Override
    public void onButtonClicked(View view) {

        Toast.makeText(this, "Confirmed", Toast.LENGTH_SHORT).show();
    }
}