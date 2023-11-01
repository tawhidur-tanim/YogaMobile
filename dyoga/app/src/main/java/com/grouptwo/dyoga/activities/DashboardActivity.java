package com.grouptwo.dyoga.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.adapters.CourseAdapter;
import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.data.viewmodels.CourseViewModel;

public class DashboardActivity extends AppCompatActivity {

    private CourseAdapter courseAdapter;
    private CourseViewModel courseViewModel;
    private EditText searchEditText;
    private RecyclerView courseListRecyclerView;
    private final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        courseAdapter = new CourseAdapter();
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        searchEditText = findViewById(R.id.searchCourse);
        courseListRecyclerView = findViewById(R.id.rc_courseList);

        initAdapter();
        initializeControls();
    }

    private void initializeControls() {
        // instantiate courseListRecyclerView
        courseListRecyclerView.setLayoutManager(layoutManager);
        courseListRecyclerView.setAdapter(courseAdapter);

        // test search
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                courseViewModel.searchCourse(s.toString());
            }
        });
    }

    private void initAdapter() {
        courseViewModel.getAllCourses().observe(this, courses ->
                courseAdapter.setCourseList(courses));

        courseAdapter.setOnItemClickListener((v, itemPosition, course) -> {
            // todo: send intent with data to view course activity
            startActivity(new Intent(this, ViewCourseActivity.class));
        });

        courseAdapter.setOnEditClickListener((v, itemPosition, course) -> {
            // todo: send intent with data to edit activity
            startActivity(new Intent(this, EditCourseActivity.class));
        });

        courseAdapter.setOnDeleteClickListener((v, itemPosition, course) -> {
            showAlertAndDelete(course);
        });
    }

    private void showAlertAndDelete(final Course course) {

        final DialogInterface.OnClickListener positiveButtonDialogInterface = (dialog, whichButton) -> {
            courseViewModel.deleteCourse(course);
            Toast.makeText(DashboardActivity.this, "Course has been deleted", Toast.LENGTH_SHORT).show();
        };

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Delete Course")
                .setMessage("Do you really want to delete course?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, positiveButtonDialogInterface)
                .setNegativeButton(android.R.string.no, null).create();
        alertDialog.show();
    }
}