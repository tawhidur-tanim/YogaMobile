package com.grouptwo.dyoga;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.grouptwo.dyoga.activities.AddClassInstanceActivity;
import com.grouptwo.dyoga.activities.AddCourseActivity;
import com.grouptwo.dyoga.activities.DashboardActivity;
import com.grouptwo.dyoga.activities.EditClassInstanceActivity;
import com.grouptwo.dyoga.activities.EditCourseActivity;
import com.grouptwo.dyoga.activities.GetStartedActivity;
import com.grouptwo.dyoga.activities.ViewCourseActivity;


public class PlaygroundActivity extends AppCompatActivity {

    private final String[] ACTIVITY_NAMES = {
            "Select an activity",
            "SplashActivity",
            "DashboardActivity",
            "AddCourseActivity",
            "EditCourseActivity",
            "AddClassInstanceActivity",
            "EditClassInstanceActivity",
            "ViewCourseActivity"
    };

    private final Class<?>[] ACTIVITIES = {
            GetStartedActivity.class,
            DashboardActivity.class,
            AddCourseActivity.class,
            EditCourseActivity.class,
            AddClassInstanceActivity.class,
            EditClassInstanceActivity.class,
            ViewCourseActivity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);

        final Spinner activitySpinner = findViewById(R.id.activitiesSpinner);

        // set up spinner content adapter
        activitySpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                ACTIVITY_NAMES)
        );

        // respond to selection of adapter
        activitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) return;

                final int itemPosition = position - 1;

                Toast.makeText(getApplicationContext(),
                        String.format("Opening %s activity now", ACTIVITY_NAMES[position]),
                        Toast.LENGTH_LONG
                ).show();

               try {
                   startActivity(new Intent(getApplicationContext(), ACTIVITIES[itemPosition]));
               }
               catch (Exception e){

                   Toast.makeText(PlaygroundActivity.this, e.toString(), Toast.LENGTH_LONG).show();
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}