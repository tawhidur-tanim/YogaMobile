package com.grouptwo.dyoga.data.repositories;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.grouptwo.dyoga.data.dYogaDatabase;
import com.grouptwo.dyoga.data.dao.ClassInstanceDao;
import com.grouptwo.dyoga.data.entities.ClassInstance;
import com.grouptwo.dyoga.interfaces.Callback;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClassInstanceRepository {

    private final ClassInstanceDao classInstanceDao;
    private final ExecutorService executor;
    private final Handler handler;

    public ClassInstanceRepository(final Application application) {
        dYogaDatabase database = dYogaDatabase.getInstance(application);
        this.classInstanceDao = database.classInstanceDao();

        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
    }

    public void add(final ClassInstance classInstance){
        executor.execute(() -> classInstanceDao.insert(classInstance));
    }

    public void update(final ClassInstance classInstance){
        executor.execute(() -> classInstanceDao.insert(classInstance));
    }

    public void queryAllByCourseId(int courseId, Callback<ClassInstance> callback) {
        executor.execute(() -> {
            final List<ClassInstance> classInstances = classInstanceDao.findAllByCourseId(courseId);
            handler.post(() -> callback.onResult(classInstances));
        });
    }

    public void delete(final ClassInstance classInstance){
        executor.execute(() -> classInstanceDao.delete(classInstance));
    }

    public void search(String keyword, Callback<ClassInstance> callback){
        executor.execute(() -> {
            List<ClassInstance> classInstances = classInstanceDao.findClassInstance(keyword);
            handler.post(() -> callback.onResult(classInstances));
        });
    }
}
