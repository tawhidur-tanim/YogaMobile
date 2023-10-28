package com.grouptwo.dyoga.data.repositories;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.color.DynamicColorsOptions;
import com.grouptwo.dyoga.data.Entities.Course;
import com.grouptwo.dyoga.data.dYogaDatabase;
import com.grouptwo.dyoga.data.dao.CourseDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CourseRepository {
    private final CourseDao courseDao;
    ExecutorService executor;
    Handler handler;


    public CourseRepository(Application application) {

        dYogaDatabase contactDatabase = dYogaDatabase.getInstance(application);
        this.courseDao = contactDatabase.courseDao();
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
    }


    public void addCourse(Course course){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                courseDao.insert(course);
            }
        });
    }

    public void addCourseList(List<Course> courses){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                courseDao.insertList(courses);
            }
        });
    }



    public void deleteCourse(Course course){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                courseDao.delete(course);
            }
        });
    }



    public LiveData<List<Course>> getAllCourses(){

        return courseDao.getAllCourses();
    }


    public LiveData<List<Course>> searchCourses(String type){

        Log.d("from repo", type);
        return courseDao.searchCourses(type);

    }


}
