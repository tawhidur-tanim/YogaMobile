package com.grouptwo.dyoga.data;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.grouptwo.dyoga.data.entities.Course;

public class dYogaFireStoreDatabase<T> {

    private static FirebaseFirestore DATABASE;

    public FirebaseFirestore getDatabase() {
        if (DATABASE == null) {
            DATABASE = FirebaseFirestore.getInstance();
        }
        return DATABASE;
    }

    public Task<DocumentReference> add(String collection, T data) {
        FirebaseFirestore fireStore = getDatabase();
        return fireStore.collection(collection).add(data);
    }
}
