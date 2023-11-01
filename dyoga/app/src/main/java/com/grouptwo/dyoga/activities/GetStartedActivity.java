package com.grouptwo.dyoga.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import com.grouptwo.dyoga.R;

public class GetStartedActivity extends AppCompatActivity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        ImageView imageView =(ImageView)findViewById(R.id.imageView3);
        imageView.setBackgroundResource(R.raw.dyoga);
    }
}