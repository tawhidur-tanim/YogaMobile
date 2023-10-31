package com.grouptwo.dyoga.components;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowId;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.customconfigs.CustomGson;
import com.grouptwo.dyoga.data.entities.Course;

import java.io.Serializable;

public class CourseBottomSheet extends BottomSheetDialogFragment {

    private BottomSheetListener bottomSheetListener;
    private final String CourseBottomSheet = "CourseBottomSheet";

    TextView titleTextView, dayText, timeText, priceText, durationText, descriptionText;
    Button courseSaveButton;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_bottom_sheet_layout, container, false);

        initControls(view);

        if (getArguments() != null) {

            Course course = CustomGson.getCustomGson().fromJson(getArguments().getString("courseObj"),Course.class);
            setCourseValue(course);
        }

        courseSaveButton.setOnClickListener( v -> {

            bottomSheetListener.onButtonClicked(v);
            dismiss();
        });


        return view;
    }

    private void setCourseValue(Course course) {
        titleTextView.setText(course.getCourseType());
        dayText.setText(course.getDayofWeek());
        timeText.setText(course.getTime());
        priceText.setText(String.valueOf(course.getPrice()));
        durationText.setText(String.valueOf(course.getDuration()));
        descriptionText.setText(course.getDescription());
    }


    // todo: need to hook the button to parent activity

    private void initControls(@NonNull View view){

        titleTextView = view.findViewById(R.id.courseTypeTitle);
        dayText = view.findViewById(R.id.bottom_sheetDay);
        timeText = view.findViewById(R.id.bottom_sheetTime);
        priceText = view.findViewById(R.id.bottom_sheetPrice);
        durationText = view.findViewById(R.id.bottom_sheetduration);
        descriptionText = view.findViewById(R.id.bottom_sheetDesc);

        courseSaveButton = view.findViewById(R.id.courseSaveButton);
    }


    @NonNull
    public static CourseBottomSheet getInstance(Course course) {
        CourseBottomSheet fragment = new CourseBottomSheet();
        Bundle args = new Bundle();

        args.putString("courseObj", CustomGson.getCustomGson().toJson(course));
        fragment.setArguments(args);
        return fragment;
    }


    public interface BottomSheetListener {
        void onButtonClicked(View view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            bottomSheetListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }
}
