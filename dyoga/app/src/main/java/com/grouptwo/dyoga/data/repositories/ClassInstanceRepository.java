package com.grouptwo.dyoga.data.repositories;

import static com.grouptwo.dyoga.utils.Constants.classInstanceCollectionName;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.grouptwo.dyoga.apiservice.RemoteService;
import com.grouptwo.dyoga.data.dYogaDatabase;
import com.grouptwo.dyoga.data.dao.ClassInstanceDao;
import com.grouptwo.dyoga.data.entities.ClassInstance;
import com.grouptwo.dyoga.interfaces.Callback;
import com.grouptwo.dyoga.utils.Constants;
import com.grouptwo.dyoga.utils.SharedPreferenceManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClassInstanceRepository implements RemoteRepository<ClassInstance> {

    private final RemoteService<ClassInstance> classInstanceRemoteService;
    private final ClassInstanceDao classInstanceDao;
    private final ExecutorService executor;
    private final Handler handler;
    private final String username;

    public ClassInstanceRepository(final Application application) {
        dYogaDatabase database = dYogaDatabase.getInstance(application);
        this.classInstanceDao = database.classInstanceDao();

        this.username = SharedPreferenceManager.getData(
                application,
                Constants.USERNAME,
                Constants.generateReference()
        );
        this.classInstanceRemoteService = new RemoteService<>(classInstanceCollectionName(username), this);

        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
    }

    public void add(final ClassInstance classInstance, Callback<Boolean> callback) {
        executor.execute(() -> {
            classInstance.setUserId(username);
            classInstance.setCreatedAt(LocalDateTime.now());
            classInstance.setUpdatedAt(LocalDateTime.now());

            long classId = classInstanceDao.insert(classInstance);
            classInstance.setId(classId);
            callback.onResult(true);

            classInstanceRemoteService.syncEntity(classInstance);

        });
    }

    public void update(final long id, final ClassInstance classInstance, Callback<Boolean> callback) {
        executor.execute(() -> {
            classInstance.setId(id);
            classInstance.setUserId(username);
            classInstance.setUpdatedAt(LocalDateTime.now());

            classInstanceDao.update(classInstance);
            callback.onResult(true);

            classInstanceRemoteService.syncEntity(classInstance);
        });
    }

    public void queryAllByCourseId(long courseId, Callback<ClassInstance> callback) {
        executor.execute(() -> {
            final List<ClassInstance> classInstances = classInstanceDao.findAllByCourseId(courseId);
            handler.post(() -> callback.onResult(classInstances));
        });
    }

    public void delete(final ClassInstance classInstance, Callback<Boolean> callback) {
        executor.execute(() -> {
            classInstance.setDeleted(true);
            classInstance.setUpdatedAt(LocalDateTime.now());
            classInstanceDao.update(classInstance);
            callback.onResult(true);

            classInstanceRemoteService.syncEntity(classInstance);
        });
    }

    public void search(String keyword, Callback<ClassInstance> callback) {
        executor.execute(() -> {
            List<ClassInstance> classInstances = classInstanceDao.findClassInstance(keyword);
            handler.post(() -> callback.onResult(classInstances));
        });
    }

    @Override
    public void updateOnly(long id, ClassInstance data) {
        executor.execute(() -> {
            data.setId(id);
            data.setUserId(username);
            data.setUpdatedAt(LocalDateTime.now());

            classInstanceDao.update(data);
        });
    }
}
