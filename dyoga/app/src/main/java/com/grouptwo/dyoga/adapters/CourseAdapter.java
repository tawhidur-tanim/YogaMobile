package com.grouptwo.dyoga.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.interfaces.ItemClickListener;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseAdapterViewHolder> {

    private final List<Course> courseList;
    private ItemClickListener<Course> onItemClickListener;
    private ItemClickListener<Course> onEditClickListener;
    private ItemClickListener<Course> onDeleteClickListener;

    public CourseAdapter() {
        courseList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CourseAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_layout, parent, false);

        return new CourseAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapterViewHolder holder, int itemPosition) {
        final Course course = courseList.get(itemPosition);

        // todo: extract these strings into strings.xml
        holder.courseName.setText(course.getCourseType());
        holder.price.setText("£ " + course.getPrice() + "");
        holder.weekDayTime.setText(course.getDayOfWeek() + " · " + DateTimeFormatter.ofPattern("hh:mm a").format(course.getCourseDate()));

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClicked(v, itemPosition, course);
            }
        });

        holder.editButton.setOnClickListener(v -> {
            if (onEditClickListener != null) {
                onEditClickListener.onItemClicked(v, itemPosition, course);
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onItemClicked(v, itemPosition, course);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public void setCourseList(List<Course> newCourseList) {
        courseList.clear();
        courseList.addAll(newCourseList);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(ItemClickListener<Course> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnEditClickListener(ItemClickListener<Course> onEditClickListener) {
        this.onEditClickListener = onEditClickListener;
    }

    public void setOnDeleteClickListener(ItemClickListener<Course> onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public static class CourseAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView courseName;
        private final TextView price;
        private final TextView weekDayTime;
        private final ImageButton editButton, deleteButton;

        public CourseAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            courseName = itemView.findViewById(R.id.courseName);
            price = itemView.findViewById(R.id.coursePrice);
            weekDayTime = itemView.findViewById(R.id.courseDayTime);
            editButton = itemView.findViewById(R.id.rvEditButton);
            deleteButton = itemView.findViewById(R.id.rvDeleteButton);
        }
    }
}
