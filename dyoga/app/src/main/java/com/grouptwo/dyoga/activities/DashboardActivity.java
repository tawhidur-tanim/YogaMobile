package com.grouptwo.dyoga.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.grouptwo.dyoga.data.viewmodels.CourseViewModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {


    private RecyclerView rc_courseList;
    private EditText dashSearch;

    private ArrayList<Course> courses = new ArrayList<>(Arrays.asList(

             new Course("Family Yoga 1", LocalDateTime.now(), 200.99,10,60,"-"),
             new Course("Family Yoga 2", LocalDateTime.now(), 200.99,10,60,"-"),
             new Course("Family Yoga 3", LocalDateTime.now(), 200.99,10,60,"-"),
             new Course("Family Yoga 4", LocalDateTime.now(), 200.99,10,60,"-"),
             new Course("Family Yoga 5", LocalDateTime.now(), 200.99,10,60,"-"),
             new Course("Family Yoga 6", LocalDateTime.now(), 200.99,10,60,"-")
     ));

     private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        try {

            initializeControls();

            courseViewModel = new ViewModelProvider(this)
                    .get(CourseViewModel.class);

            CourseAdapter courseAdapter = new CourseAdapter(courses);
            rc_courseList.setAdapter(courseAdapter);
            courseViewModel.getAllCourses().observe(this,
                    new Observer<List<Course>>() {
                        @Override
                        public void onChanged(List<Course> courses) {

                            if(courses == null){
                                Toast.makeText(DashboardActivity.this, "null", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            courseAdapter.setCourseList(courses);
                        }
                    });

            // testing db connection
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    dYogaDatabase db =  dYogaDatabase.getInstance(getApplicationContext());
//
//                    db.courseDao().insertList(courses);
//
////                    Course course = db.courseDao().FindFist();
////                    Log.d("Main Activity", "get completed \n"+course.getId() + "\n"+course.getCourseType());
//                }
//            }).start();



        }
        catch (Exception e){

            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }


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