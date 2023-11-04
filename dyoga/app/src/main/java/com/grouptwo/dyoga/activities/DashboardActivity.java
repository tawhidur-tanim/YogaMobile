package com.grouptwo.dyoga.activities;

import static com.grouptwo.dyoga.utils.Constants.COURSE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.adapters.CourseAdapter;
import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.data.repositories.ClassInstanceRepository;
import com.grouptwo.dyoga.data.repositories.CourseRepository;
import com.grouptwo.dyoga.data.viewmodels.CourseViewModel;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private CourseAdapter courseAdapter;
    private CourseViewModel courseViewModel;
    private ImageButton addCourseButton, logoutButton;
    private EditText searchEditText;
    private RecyclerView courseListRecyclerView;
    private View emptyCourseView;
    private final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        firebaseAuth = FirebaseAuth.getInstance();

        courseAdapter = new CourseAdapter();
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        emptyCourseView = findViewById(R.id.emptyCourseView);
        addCourseButton = findViewById(R.id.addCourseButton);
        logoutButton = findViewById(R.id.logoutButton);
        searchEditText = findViewById(R.id.searchCourse);
        courseListRecyclerView = findViewById(R.id.courseListRecyclerView);

        initAdapter();
        initializeControls();
    }

    private void initializeControls() {
        // instantiate courseListRecyclerView
        courseListRecyclerView.setLayoutManager(layoutManager);
        courseListRecyclerView.setAdapter(courseAdapter);

        // text search
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                courseViewModel.queryCourses(s.toString());
            }
        });

        addCourseButton.setOnClickListener(v -> startActivity(new Intent(this, AddCourseActivity.class)));

        logoutButton.setOnClickListener(v -> {
            // todo: clear local database upon logout
            firebaseAuth.signOut();
            clearAllFromDb();

            startActivity(new Intent(this, GetStartedActivity.class));
            finishAfterTransition();
        });
    }

    private void clearAllFromDb() {
        CourseRepository courseRepository = new CourseRepository(getApplicationContext());
        ClassInstanceRepository classInstanceRepository = new ClassInstanceRepository(getApplication());
        courseRepository.truncate();
        classInstanceRepository.truncate();
    }

    private void initAdapter() {
        // trigger view modal data model
        courseViewModel.queryCourses();
        courseViewModel.getAllCourses().observe(this, courses -> {
            if(courses.isEmpty()) {
                searchEditText.setVisibility(View.GONE);
                emptyCourseView.setVisibility(View.VISIBLE);
            } else {
                searchEditText.setVisibility(View.VISIBLE);
                emptyCourseView.setVisibility(View.GONE);
            }

            courseAdapter.setCourseList(courses);
        });

        courseAdapter.setOnItemClickListener((v, itemPosition, course) -> {
            Intent intent = new Intent(this, ViewCourseActivity.class);
            intent.putExtra(COURSE, course);
            startActivity(intent);
        });

        courseAdapter.setOnEditClickListener((v, itemPosition, course) -> {
            Intent intent = new Intent(this, EditCourseActivity.class);
            intent.putExtra(COURSE, course);
            startActivity(intent);
        });

        courseAdapter.setOnDeleteClickListener((v, itemPosition, course)
                -> showAlertAndDelete(course));
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