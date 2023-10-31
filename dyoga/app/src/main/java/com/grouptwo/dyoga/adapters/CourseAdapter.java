package com.grouptwo.dyoga.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.data.entities.ClassInstance;
import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.interfaces.ItemClickListener;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder>{

    private List<Course> courseList;

    public ItemClickListener<Course> clickListener;


    public CourseAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.course_layout,parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Course course = courseList.get(position);

        holder.courseName.setText(course.getCourseType());
        holder.price.setText("£ "+course.getPrice() + "");

        holder.weekDayTime.setText(course.getDayofWeek() + " - " + DateTimeFormatter.ofPattern("hh:mm a").format(course.getCourseDate()));

    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener<Course> myListener){
        this.clickListener = myListener;
    }

    public class MyViewHolder extends
            RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView courseName;
        TextView price;
        TextView weekDayTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.courseName);
            price = itemView.findViewById(R.id.coursePrice);
            weekDayTime = itemView.findViewById(R.id.courseDayTime);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null){
                final Course course = courseList.get(getAdapterPosition());
                clickListener.onItemClicked(v, getAdapterPosition(), course);
            } else {
                Log.e(this.getClass().getName(), "Item click listener is null");
            }
        }
    }
}
