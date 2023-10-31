package com.grouptwo.dyoga.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.adapters.CourseAdapter;
import com.grouptwo.dyoga.data.dYogaDatabase;
import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.data.repositories.CourseRepository;
import com.grouptwo.dyoga.data.viewmodels.CourseViewModel;
import com.grouptwo.dyoga.interfaces.ButtonClickListener;
import com.grouptwo.dyoga.interfaces.Callback;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {


    private RecyclerView rc_courseList;
    private EditText dashSearch;

    private ArrayList<Course> courses = new ArrayList<>(Arrays.asList(

             new Course("Family Yoga 1", LocalDateTime.now(), 200.99,10,60,"-","Monday"),
             new Course("Family Yoga 2", LocalDateTime.now(), 200.99,10,60,"-","Monday"),
             new Course("Family Yoga 3", LocalDateTime.now(), 200.99,10,60,"-","Monday"),
             new Course("Family Yoga 4", LocalDateTime.now(), 200.99,10,60,"-","Monday"),
             new Course("Family Yoga 5", LocalDateTime.now(), 200.99,10,60,"-","Monday"),
             new Course("Family Yoga 6", LocalDateTime.now(), 200.99,10,60,"-","Monday")
     ));

     private CourseViewModel courseViewModel;

     private CourseRepository courseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeControls();
        pullAndObserveData();

        courseRepository = new CourseRepository(getApplication());


    }

    private void pullAndObserveData() {
        courseViewModel = new ViewModelProvider(this)
                .get(CourseViewModel.class);

        CourseAdapter courseAdapter = new CourseAdapter(courses);
        rc_courseList.setAdapter(courseAdapter);
        courseViewModel.getAllCourses().observe(this,
                courses -> courseAdapter.setCourseList(courses));


        courseAdapter.setClickListener(new ButtonClickListener<Course>() {
            @Override
            public void onEditClick(View view, int itemPosition, Course item) {

            }

            @Override
            public void onDeleteClick(View view, int itemPosition, Course item) {

                showAlertAndDelete(item);

            }
        });
    }

    private void showAlertAndDelete(Course item) {
        new AlertDialog.Builder(DashboardActivity.this)
                .setTitle("Delete Course")
                .setMessage("Do you really want to delete course?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        courseRepository.getCourseById(item.getId(), new Callback<Course>() {
                            @Override
                            public void onResult(Course result) {
                                courseViewModel.deleteCourse(result);


                                Toast.makeText(DashboardActivity.this, "Course has been deleted"
                                        , Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onResult(List<Course> result) {
                            }
                        });
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }


    void initializeControls() {

        rc_courseList = findViewById(R.id.rc_courseList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rc_courseList.setLayoutManager(layoutManager);

        dashSearch = findViewById(R.id.searchCourse);

        // test search
        dashSearch.addTextChangedListener(new TextWatcher() {
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
}