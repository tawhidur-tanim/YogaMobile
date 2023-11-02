package com.grouptwo.dyoga.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.adapters.DaysOfWeekAdapter;
import com.grouptwo.dyoga.adapters.YogaDurationAdapter;
import com.grouptwo.dyoga.adapters.YogaTypesAdapter;
import com.grouptwo.dyoga.data.viewmodels.CourseViewModel;
import com.grouptwo.dyoga.enums.CourseActions;

import java.time.LocalDateTime;

public class AddClassInstanceActivity extends AppCompatActivity {


    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_class_instance);
    }
}