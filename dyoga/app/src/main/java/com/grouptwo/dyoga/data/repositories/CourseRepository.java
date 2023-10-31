package com.grouptwo.dyoga.data.repositories;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.grouptwo.dyoga.apiservice.CourseApiService;
import com.grouptwo.dyoga.apiservice.HttpClient;
import com.grouptwo.dyoga.apiservice.models.Result;
import com.grouptwo.dyoga.data.dYogaDatabase;
import com.grouptwo.dyoga.data.dao.CourseDao;
import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.interfaces.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class CourseRepository {
    private final CourseDao courseDao;
    ExecutorService executor;
    Handler handler;

    Application application;
    public CourseRepository(Application application) {

        dYogaDatabase contactDatabase = dYogaDatabase.getInstance(application);
        this.courseDao = contactDatabase.courseDao();
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        this.application = application;
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

     public void saveCourseAPI(Course course)
     {
         CourseApiService courseApiService = HttpClient.getInstance().create(CourseApiService.class);
         Call<Result> call = courseApiService.saveCourse(course);

         call.enqueue(new retrofit2.Callback<Result>() {
             @Override
             public void onResponse(Call<Result> call, Response<Result> response) {
                 Result result = response.body();
             }

             @Override
             public void onFailure(Call<Result> call, Throwable t) {

             }
         });
     }



}
