package com.grouptwo.dyoga.data.repositories;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.grouptwo.dyoga.data.dYogaDatabase;
import com.grouptwo.dyoga.data.dYogaFireStoreDatabase;
import com.grouptwo.dyoga.data.dao.CourseDao;
import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.interfaces.Callback;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CourseRepository {

    private final static String COLLECTION_NAME = "courses";
    private final dYogaFireStoreDatabase<Course> dYogaFireStoreDatabase;
    private final ExecutorService executor;
    private final CourseDao courseDao;
    private final Handler handler;

    public CourseRepository(final Context application) {
        dYogaDatabase contactDatabase = dYogaDatabase.getInstance(application);
        dYogaFireStoreDatabase = new dYogaFireStoreDatabase<>();

        this.courseDao = contactDatabase.courseDao();
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
    }

    public void save(final Course course, final Callback<Boolean> callback) {
        executor.execute(() -> {
            course.setCreatedAt(LocalDateTime.now());
            course.setUpdatedAt(LocalDateTime.now());

            courseDao.insert(course);
            callback.onResult(true);
        });
    }

    public void update(final int id, final Course course, Callback<Boolean> callback) {
        executor.execute(() -> {
            course.setId(id);
            course.setUpdatedAt(LocalDateTime.now());

            courseDao.update(course);
            callback.onResult(true);
        });
    }

    public void delete(Course course, Callback<Course> callback) {
        executor.execute(() -> {
            courseDao.delete(course);
            callback.onResult(course);
        });
    }

    public void findAll(Callback<Course> callback) {
        executor.execute(() -> {
            List<Course> courses = courseDao.getAllCourses();
            handler.post(() -> callback.onResult(courses));
        });
    }

    public void searchCourses(String type, Callback<Course> callback) {
        executor.execute(() -> {
            List<Course> courses = courseDao.searchCourses(type);
            handler.post(() -> callback.onResult(courses));
        });
    }

//    public void getCourseById(int id, Callback<Course> callback) {
//        executor.execute(() -> {
//            Course course = courseDao.getCourseById(id);
//            handler.post(() -> callback.onResult(course));
//        });
//    }

//    public void saveCourseAPI(Course course) {
//        CourseApiService courseApiService = HttpClient.getInstance().create(CourseApiService.class);
//        Call<Result> call = courseApiService.saveCourse(course);
//
//        call.enqueue(new retrofit2.Callback<Result>() {
//            @Override
//            public void onResponse(Call<Result> call, Response<Result> response) {
//                Result result = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<Result> call, Throwable t) {
//
//            }
//        });
//    }
}
