package com.grouptwo.dyoga.data.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.data.repositories.CourseRepository;
import com.grouptwo.dyoga.interfaces.Callback;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    private final CourseRepository courseRepository;
    private final static MutableLiveData<List<Course>> allCourses = new MutableLiveData<>();

    public CourseViewModel(@NonNull Application application) {
        super(application);
        courseRepository = new CourseRepository(application);
    }

    public void queryCourses() {
        courseRepository.findAll(new Callback<Course>() {
            @Override
            public void onResult(List<Course> result) {
                allCourses.postValue(result);
            }
        });
    }

    public void queryCourses(@Nullable String type){
        courseRepository.searchCourses(type, new Callback<Course>() {
            @Override
            public void onResult(List<Course> result) {
                allCourses.postValue(result);
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
                queryCourses();
            }
        });
    }

    public void save(final Course course, final Callback<Boolean> callback) {
        courseRepository.save(course, new Callback<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                queryCourses();
                callback.onResult(result);
            }
        });
    }

    public void update(final int id, final Course course, Callback<Boolean> callback) {
        courseRepository.update(id, course, new Callback<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                queryCourses();
                callback.onResult(result);
            }
        });
    }
}
