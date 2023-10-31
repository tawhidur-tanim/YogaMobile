package com.grouptwo.dyoga.apiservice;

import com.grouptwo.dyoga.apiservice.models.Result;
import com.grouptwo.dyoga.data.entities.Course;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CourseApiService {

    @POST("api/SaveCourse")
    Call<Result> saveCourse(Course course);
}
