package com.grouptwo.dyoga.activities;

import android.os.Bundle;
import android.util.Log;
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
import java.time.format.DateTimeFormatter;

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

    private final String bottomSheetTag = "courseBottomSheet";
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
                // todo: show review fragment here

                try {
                    CourseBottomSheet bottomSheet = CourseBottomSheet.getInstance(getCourse());
                    bottomSheet.show(getSupportFragmentManager(), bottomSheetTag);
                }
                catch (Exception e){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                }
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

    private Course getCourse(){

        LocalDateTime dateTime = LocalDateTime.parse("1900-01-01 "+timeButton.getText().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"));
        Course course = new Course(yogaTypesSpinner.getSelectedItem().toString()
                ,dateTime,DoubleConverter.convert(priceEditText.getText().toString())
                , Integer.parseInt(capacityEditText.getText().toString())
                ,Integer.parseInt((durationSpinner.getSelectedItem().toString().replace("minutes","").trim()))
                ,descriptionEditText.getText().toString(),daysOfWeekSpinner.getSelectedItem().toString());

        return course;
    }

    @Override
    public void onButtonClicked(View view) {

        Toast.makeText(this, "Confirmed", Toast.LENGTH_SHORT).show();
    }
}