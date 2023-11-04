package com.grouptwo.dyoga.activities;

import static com.grouptwo.dyoga.utils.Constants.COURSE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.components.YogaDatePicker;
import com.grouptwo.dyoga.data.entities.ClassInstance;
import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.data.viewmodels.ClassInstanceViewModel;
import com.grouptwo.dyoga.enums.ClassInstanceActions;
import com.grouptwo.dyoga.interfaces.Callback;

import java.time.LocalDateTime;

public class AddClassInstanceActivity extends AppCompatActivity implements Callback<Boolean> {

    protected Button dateButton, saveButton;
    protected ImageButton deleteButton;
    protected ImageView addDescriptionImageView;
    protected TextView screenTitle, screenDescription, addDescriptionTextView;
    protected LinearLayout descriptionDisplay;
    protected EditText instrcutorEditText, roomEditText, descriptionEditText;
    protected ClassInstanceActions classInstanceAction;
    protected LocalDateTime yogaDate, createdAt;
    protected long classId;

    private Handler handler;
    private ClassInstanceViewModel classInstanceViewModel;

    protected Course course;

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

        yogaDate = LocalDateTime.now();
        createdAt = LocalDateTime.now();

        saveButton = findViewById(R.id.saveClassButton);
        dateButton = findViewById(R.id.dateButton);
        deleteButton = findViewById(R.id.deleteButton);
        screenTitle = findViewById(R.id.clasScreenHeading);
        screenDescription = findViewById(R.id.screenDescription);
        addDescriptionTextView = findViewById(R.id.classAddDescriptionTextView);
        addDescriptionImageView = findViewById(R.id.classAddDescriptionImageView);
        descriptionDisplay = findViewById(R.id.classDescriptionDisplay);

        descriptionEditText = findViewById(R.id.classDescriptionEditText);
        instrcutorEditText = findViewById(R.id.classInstructorEditText);
        roomEditText = findViewById(R.id.roomEditText);

        setButtonListeners();

        // set current date
        dateButton.setText(YogaDatePicker.getCurrentDateText());

        // set screen description
        this.screenDescription.setText(String.format(
                getString(R.string.s_a_new_class_instance_to_family_yoga_by_time),
                getString(R.string.add),
                course.getType(),
                course.getTimeText())
        );
    }


    private void setButtonListeners() {
        final View.OnClickListener descriptionClickListener = v -> {
            hideDescriptionCTA();
            descriptionEditText.requestFocus();
        };

        addDescriptionTextView.setOnClickListener(descriptionClickListener);
        addDescriptionImageView.setOnClickListener(descriptionClickListener);

        dateButton.setOnClickListener(v -> YogaDatePicker.show(yogaDate, yogaDatePicker -> {
            dateButton.setText(yogaDatePicker.getDateText());
            yogaDate = yogaDatePicker.getLocalDate();
        }, getSupportFragmentManager()));

        saveButton.setOnClickListener(v -> {
            if (!isInputsValid()) {
                return;
            }
            ClassInstance classInstance = getClassInstance();

            if (classInstanceAction == ClassInstanceActions.NEW) {
                classInstanceViewModel.add(classInstance, this);

            } else {
                classInstanceViewModel.update(classId, classInstance, this);
            }

        });

        deleteButton.setOnClickListener(v -> {
            ClassInstance classInstance = getClassInstance();
            classInstance.setId(classId);

            classInstanceAction = ClassInstanceActions.DELETED;

            showAlertAndDelete(classInstance);
        });
    }

    private void showAlertAndDelete(final ClassInstance classInstance) {
        final DialogInterface.OnClickListener positiveButtonDialogInterface = (dialog, whichButton) -> {
            classInstanceViewModel.delete(classInstance, AddClassInstanceActivity.this);
        };

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.delete_class)
                .setMessage(R.string.do_you_really_want_to_delete_this_class)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, positiveButtonDialogInterface)
                .setNegativeButton(android.R.string.no, null).create();
        alertDialog.show();
    }

    @Override
    public void onResult(Boolean result) {
        if (result == null || !result) {
            showToast(R.string.an_error_occurred_saving_class_info_please_try_again);
            return;
        }

        if (classInstanceAction == ClassInstanceActions.NEW) {
            showToast(R.string.class_has_been_added);
        }

        if (classInstanceAction == ClassInstanceActions.UPDATE) {
            showToast(R.string.class_has_been_updated);
        }

        if (classInstanceAction == ClassInstanceActions.DELETED) {
            showToast(R.string.class_has_been_deleted);
        }

        finish();
    }

    private ClassInstance getClassInstance() {
        final String comments = descriptionEditText.getText().toString();
        final String instructor = instrcutorEditText.getText().toString();
        final String room = roomEditText.getText().toString();

        final ClassInstance classInstance = new ClassInstance(course.getId(), yogaDate, instructor, room, comments);
        classInstance.setCreatedAt(createdAt);
        return classInstance;
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

        if (!yogaDate.getDayOfWeek().toString().equalsIgnoreCase(course.getDayOfWeek())) {
            Toast.makeText(getApplicationContext(), String.format(getApplicationContext().getString(R.string.day_of_the_week_must_match_the_course),
                    course.getDayOfWeek()), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    protected void hideDescriptionCTA() {
        descriptionDisplay.setVisibility(View.VISIBLE);
        addDescriptionTextView.setVisibility(View.GONE);
        addDescriptionImageView.setVisibility(View.GONE);
    }

    private void showToast(int message) {
        handler.post(() -> Toast.makeText(getApplicationContext(),
                message, Toast.LENGTH_LONG).show());
    }
}