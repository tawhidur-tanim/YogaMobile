package com.grouptwo.dyoga.data.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.grouptwo.dyoga.data.Entities.Course;
import com.grouptwo.dyoga.data.repositories.CourseRepository;
import com.grouptwo.dyoga.interfaces.Callback;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    private CourseRepository courseRepository;
    private MutableLiveData<List<Course>> allCourses = new MutableLiveData<>();


    public CourseViewModel(@NonNull Application application) {
        super(application);
        this.courseRepository = new CourseRepository(application);


    }

    public LiveData<List<Course>> getAllCourses(){

        courseRepository.getAllCourses(new Callback<Course>() {
            @Override
            public void onResult(Course result) {
            }

            @Override
            public void onResult(List<Course> result) {
                allCourses.setValue(result);
            }
        });
        return allCourses;
    }


    public void addCourse(Course course){

        courseRepository.addCourse(course);
    }

    public void addCourseList(List<Course> courses){

        courseRepository.addCourseList(courses);
    }

    public void deleteCourse(Course course){

        courseRepository.deleteCourse(course);
    }

    public void searchCourse(String type){

        courseRepository.searchCourses(type, new Callback<Course>() {
            @Override
            public void onResult(Course result) {
            }

            @Override
            public void onResult(List<Course> result) {
                allCourses.setValue(result);
            }
        });
    }

}
