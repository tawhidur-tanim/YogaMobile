package com.grouptwo.dyoga.data.repositories;

import static com.grouptwo.dyoga.utils.Constants.classInstanceCollectionName;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.grouptwo.dyoga.apiservice.RemoteService;
import com.grouptwo.dyoga.configs.GsonConfig;
import com.grouptwo.dyoga.data.GlobalClassInstance;
import com.grouptwo.dyoga.data.dYogaDatabase;
import com.grouptwo.dyoga.data.dao.ClassInstanceDao;
import com.grouptwo.dyoga.data.dao.CourseDao;
import com.grouptwo.dyoga.data.entities.ClassInstance;
import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.interfaces.Callback;
import com.grouptwo.dyoga.utils.Constants;
import com.grouptwo.dyoga.utils.SharedPreferenceManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClassInstanceRepository implements RemoteRepository<ClassInstance> {

    private final RemoteService<ClassInstance> classInstanceRemoteService;
    private final RemoteService<GlobalClassInstance> globalClassInstanceRemoteService;
    private final ClassInstanceDao classInstanceDao;
    private final CourseDao courseDao;
    private final ExecutorService executor;
    private final Handler handler;
    private final String username;

    private static final String CLASS_INSTANCES = "classInstances";

    public ClassInstanceRepository(final Context application) {
        dYogaDatabase database = dYogaDatabase.getInstance(application);
        this.classInstanceDao = database.classInstanceDao();
        this.courseDao = database.courseDao();

        this.username = SharedPreferenceManager.getData(
                application,
                Constants.USERNAME,
                Constants.generateReference()
        );
        this.globalClassInstanceRemoteService = new RemoteService<>(CLASS_INSTANCES);
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
            globalClassInstanceRemoteService.syncEntity(asGlobal(classInstance));
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
            globalClassInstanceRemoteService.syncEntity(asGlobal(classInstance));
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
            globalClassInstanceRemoteService.syncEntity(asGlobal(classInstance));
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

    public void saveOnly(final ClassInstance classInstance) {
        executor.execute(() -> {
            classInstance.setUserId(username);
            classInstance.setCreatedAt(LocalDateTime.now());
            classInstance.setUpdatedAt(LocalDateTime.now());

            classInstanceDao.insert(classInstance);
        });
    }

    public void truncate() {
        executor.execute(classInstanceDao::truncate);
    }

    public static void sync(Context context, Callback<Boolean> callback) {
        final ClassInstanceRepository classInstanceRepository = new ClassInstanceRepository(context);
        classInstanceRepository.syncData(callback);
    }

    private void syncData(Callback<Boolean> callback) {
        classInstanceRemoteService.syncRemoteEntities(new Callback<Map<String, Object>>() {
            @Override
            public void onResult(List<Map<String, Object>> result) {
                final Gson gson = GsonConfig.gson();
                for (Map<String, Object> data : result) {
                    final String jsonString = gson.toJson(data);
                    final ClassInstance classInstance = gson.fromJson(jsonString, ClassInstance.class);

                    saveOnly(classInstance);

                    Log.d("classInstance", classInstance.getId() + " => " + jsonString);
                }

                callback.onResult(true);
            }
        });
    }

    private GlobalClassInstance asGlobal(ClassInstance classInstance) {
        Course course = courseDao.getCourseById(Math.toIntExact(classInstance.getCourseId()));

        // maintain a unique id for each global class instance
        String hashedId = Constants.generateReferenceById(classInstance.getId());
        classInstance.setReference(username.substring(0, 6).concat(hashedId));

        return new GlobalClassInstance(course, classInstance);
    }
}
