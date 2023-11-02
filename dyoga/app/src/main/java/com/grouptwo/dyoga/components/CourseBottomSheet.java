package com.grouptwo.dyoga.components;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.configs.GsonConfig;
import com.grouptwo.dyoga.data.entities.Course;

import java.time.format.DateTimeFormatter;
import java.util.Map;

public class CourseBottomSheet extends BottomSheetDialogFragment {

    private CourseData courseData;
    private BottomSheetListener bottomSheetListener;
    private final static String TAG = "COURSE_BOTTOM_SHEET";
    private final static String COURSE_OBJ = "COURSE_OBJECT";
    public final static String DURATION_TEXT = "DURATION_TEXT";
    private TextView titleTextView, dayText, timeText, priceText, durationTextView, descriptionText, descriptionLabel;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.course_bottom_sheet_layout, container, false);

        titleTextView = view.findViewById(R.id.courseTypeTitle);
        dayText = view.findViewById(R.id.bottom_sheetDay);
        timeText = view.findViewById(R.id.bottom_sheetTime);
        priceText = view.findViewById(R.id.bottom_sheetPrice);
        durationTextView = view.findViewById(R.id.bottom_sheetduration);
        descriptionText = view.findViewById(R.id.bottom_sheetDesc);
        descriptionLabel = view.findViewById(R.id.btmdescriptionLabel);

        final Button courseSaveButton = view.findViewById(R.id.courseSaveButton);
        courseSaveButton.setOnClickListener(v -> {
            bottomSheetListener.onButtonClicked(courseData);
            dismiss();
        });

        populateView();

        return view;
    }

    private void populateView() {
        if (getArguments() == null) return;

        courseData = GsonConfig.gson().fromJson(getArguments().getString(COURSE_OBJ), CourseData.class);
        final Course course = courseData.getCourse();
        final Map<String, String> extras = courseData.getExtras();
        final String durationText = extras.getOrDefault(DURATION_TEXT, "-");

        titleTextView.setText(course.getType());
        dayText.setText(course.getDayOfWeek());
        timeText.setText(course.getTime().format(DateTimeFormatter.ofPattern("hh:mm a")));
        priceText.setText(String.valueOf(course.getPrice()));
        descriptionText.setText(course.getDescription());
        durationTextView.setText(durationText);

        // set visibility based on data
        descriptionLabel.setVisibility(course.getDescription().isEmpty() ? View.GONE : View.VISIBLE);
        descriptionText.setVisibility(course.getDescription().isEmpty() ? View.GONE : View.VISIBLE);
    }

    public static void show(CourseData courseData, FragmentManager fragmentManager) {
        final CourseBottomSheet courseBottomSheet = new CourseBottomSheet();

        final Bundle args = new Bundle();
        args.putString(COURSE_OBJ, GsonConfig.gson().toJson(courseData));
        courseBottomSheet.setArguments(args);

        courseBottomSheet.show(fragmentManager, TAG);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            bottomSheetListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement BottomSheetListener");
        }
    }

    public interface BottomSheetListener {
        void onButtonClicked(CourseData courseData);
    }

    public static class CourseData {
        private final Course course;
        private final Map<String, String> extras;

        public CourseData(Course course, Map<String, String> extras) {
            this.course = course;
            this.extras = extras;
        }

        public Course getCourse() {
            return course;
        }

        public Map<String, String> getExtras() {
            return extras;
        }
    }
}
