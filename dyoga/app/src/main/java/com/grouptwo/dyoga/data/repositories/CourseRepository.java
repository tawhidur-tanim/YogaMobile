package com.grouptwo.dyoga.data.repositories;

import static com.grouptwo.dyoga.utils.Constants.courseCollectionName;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.grouptwo.dyoga.apiservice.RemoteService;
import com.grouptwo.dyoga.data.dYogaDatabase;
import com.grouptwo.dyoga.data.dao.CourseDao;
import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.interfaces.Callback;
import com.grouptwo.dyoga.utils.Constants;
import com.grouptwo.dyoga.utils.SharedPreferenceManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CourseRepository implements RemoteRepository<Course> {

    private final RemoteService<Course> courseRemoteService;
    private final ExecutorService executor;
    private final CourseDao courseDao;
    private final Handler handler;
    private final String username;

    public CourseRepository(final Context application) {
        dYogaDatabase contactDatabase = dYogaDatabase.getInstance(application);
        this.courseDao = contactDatabase.courseDao();

        this.username = SharedPreferenceManager.getData(
                application,
                Constants.USERNAME,
                Constants.generateReference()
        );
        this.courseRemoteService = new RemoteService<>(courseCollectionName(username), this);

        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
    }

    public void save(final Course course, final Callback<Boolean> callback) {
        executor.execute(() -> {
            course.setUserId(username);
            course.setCreatedAt(LocalDateTime.now());
            course.setUpdatedAt(LocalDateTime.now());

            final long courseId = courseDao.insert(course);
            course.setId(courseId);
            callback.onResult(true);

            courseRemoteService.syncEntity(course);
        });
    }

    public void update(final long id, final Course course, Callback<Boolean> callback) {
        executor.execute(() -> {
            course.setId(id);
            course.setUserId(username);
            course.setUpdatedAt(LocalDateTime.now());

            courseDao.update(course);
            callback.onResult(true);

            courseRemoteService.syncEntity(course);
        });
    }

    @Override
    public void updateOnly(final long id, final Course course) {
        executor.execute(() -> {
            course.setId(id);
            course.setUserId(username);
            course.setUpdatedAt(LocalDateTime.now());

            courseDao.update(course);
        });
    }

    public void delete(Course course, Callback<Course> callback) {
        executor.execute(() -> {
            course.setDeleted(true);
            course.setUserId(username);
            course.setUpdatedAt(LocalDateTime.now());

            courseDao.update(course);
            callback.onResult(course);

            courseRemoteService.syncEntity(course);
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
}
