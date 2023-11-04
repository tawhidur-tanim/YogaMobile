package com.grouptwo.dyoga.apiservice;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grouptwo.dyoga.configs.GsonConfig;

import java.util.Map;

public class dYogaFireStoreDatabase<T> {

    private final FirebaseFirestore firebaseFirestore;

    public dYogaFireStoreDatabase() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public Task<Void> addOrUpdate(String collection, String id, T data) {
        final String json = GsonConfig.gson().toJson(data);
        final Map<String, Object> dataJson = GsonConfig.gson().fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());
        return firebaseFirestore.collection(collection).document(id).set(dataJson);
    }
}
