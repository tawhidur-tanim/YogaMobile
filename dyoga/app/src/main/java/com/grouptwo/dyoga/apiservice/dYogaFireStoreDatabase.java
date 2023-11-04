package com.grouptwo.dyoga.apiservice;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.reflect.TypeToken;
import com.grouptwo.dyoga.configs.GsonConfig;
import com.grouptwo.dyoga.interfaces.Callback;

import java.util.ArrayList;
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

    public void getDocuments(String collection, Callback<Map<String, Object>> callback) {
        firebaseFirestore.collection(collection).get().addOnCompleteListener(task -> {
            final ArrayList<Map<String, Object>> documents = new ArrayList<>();
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d(this.getClass().getName(), document.getId() + " => " + document.getData());
                    documents.add(document.getData());
                }
            } else {
                Log.d(this.getClass().getName(), "Error getting documents: ", task.getException());
            }

            callback.onResult(documents);
        });
    }
}
