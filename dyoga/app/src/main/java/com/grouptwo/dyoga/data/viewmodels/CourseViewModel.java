package com.grouptwo.dyoga.data.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.data.repositories.CourseRepository;
import com.grouptwo.dyoga.interfaces.Callback;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    private final CourseRepository courseRepository;
    private final MutableLiveData<List<Course>> allCourses;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        allCourses = new MutableLiveData<>();
        courseRepository = new CourseRepository(application);

        findAllCourses();
    }

    private void findAllCourses() {
        courseRepository.findAll(new Callback<Course>() {
            @Override
            public void onResult(List<Course> result) {
                allCourses.setValue(result);
            }
        });
    }

    public LiveData<List<Course>> getAllCourses(){
        return allCourses;
    }

    public void deleteCourse(Course course){
        courseRepository.delete(course, new Callback<Course>() {
            @Override
            public void onResult(Course result) {
                findAllCourses();
            }
        });
    }

    public void searchCourse(String type){
        courseRepository.searchCourses(type, new Callback<Course>() {
            @Override
            public void onResult(List<Course> result) {
                allCourses.setValue(result);
            }
        });
    }
}
