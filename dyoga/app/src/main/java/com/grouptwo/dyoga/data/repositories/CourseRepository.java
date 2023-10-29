package com.grouptwo.dyoga.data.repositories;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.grouptwo.dyoga.data.dYogaDatabase;
import com.grouptwo.dyoga.data.dao.CourseDao;
import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.interfaces.Callback;

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



    public void getAllCourses(Callback<Course> callback){
        executor.execute(() -> {
            List<Course> courses = courseDao.getAllCourses();
            new Handler(Looper.getMainLooper()).post(() -> {
                callback.onResult(courses);
            });
        });
    }


    public void searchCourses(String type,Callback<Course> callback){

        executor.execute(() -> {
            List<Course> courses = courseDao.searchCourses(type);
            new Handler(Looper.getMainLooper()).post(() -> {
                callback.onResult(courses);
            });
        });
    }



}
