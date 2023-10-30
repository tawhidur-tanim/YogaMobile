package com.grouptwo.dyoga.components;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowId;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.data.entities.Course;

import java.io.Serializable;

public class CourseBottomSheet extends BottomSheetDialogFragment {


    private final String CourseBottomSheet = "CourseBottomSheet";

    TextView titleTextView, dayText, timeText, priceText, durationText, descriptionText;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_bottom_sheet_layout, container, false);

        initControls(view);

        if (getArguments() != null) {
            Course course = new Gson().fromJson(getArguments().getString("courseObj"),Course.class);


            // todo: need to assign values
        }


        return view;
    }


    // todo: need to hook the button to parent activity

    private void initControls(View view){

        titleTextView = view.findViewById(R.id.bottom_sheetTitle);
        dayText = view.findViewById(R.id.bottom_sheetDay);
        timeText = view.findViewById(R.id.bottom_sheetTime);
        priceText = view.findViewById(R.id.bottom_sheetPrice);
        durationText = view.findViewById(R.id.bottom_sheetduration);
        descriptionText = view.findViewById(R.id.bottom_sheetDesc);
    }


    public static CourseBottomSheet getInstance(Course course) {
        CourseBottomSheet fragment = new CourseBottomSheet();
        Bundle args = new Bundle();

        args.putString("courseObj", new Gson().toJson(course));
        fragment.setArguments(args);
        return fragment;
    }
}
