package com.grouptwo.dyoga.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.adapters.CourseAdapter;
import com.grouptwo.dyoga.data.entities.Course;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class DashboardActivity extends AppCompatActivity {


    RecyclerView rc_courseList;


     ArrayList<Course> courses = new ArrayList<>(Arrays.asList(

             new Course("Family Yoga 1", LocalDateTime.now(), 200.99),
             new Course("Family Yoga 2", LocalDateTime.now(), 200.99),
             new Course("Family Yoga 3", LocalDateTime.now(), 200.99),
             new Course("Family Yoga 4", LocalDateTime.now(), 200.99),
             new Course("Family Yoga 5", LocalDateTime.now(), 200.99),
             new Course("Family Yoga 6", LocalDateTime.now(), 200.99)

     ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        try {

            initializeControls();
            CourseAdapter courseAdapter = new CourseAdapter(courses);
            rc_courseList.setAdapter(courseAdapter);
        }
        catch (Exception e){

            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }



    void initializeControls() {

        rc_courseList = findViewById(R.id.rc_courseList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rc_courseList.setLayoutManager(layoutManager);
    }
}