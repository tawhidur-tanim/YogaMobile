package com.grouptwo.dyoga.apiservice;

import com.grouptwo.dyoga.data.entities.RemoteEntity;
import com.grouptwo.dyoga.data.repositories.RemoteRepository;
import com.grouptwo.dyoga.utils.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RemoteService<T extends RemoteEntity> {

    private final String collectionName;
    private final RemoteRepository<T> repository;

    private final dYogaApiClient apiClient;
    private final ExecutorService executorService;
    private final dYogaFireStoreDatabase<T> fireStoreDatabase;

    private final boolean fireStoreSync = true, apiStoreSync = true;

    public RemoteService(String collectionName, RemoteRepository<T> repository) {
        this.repository = repository;
        this.collectionName = collectionName;

        this.apiClient = new dYogaApiClient();
        this.executorService = Executors.newSingleThreadExecutor();
        this.fireStoreDatabase = new dYogaFireStoreDatabase<>();
    }

    public void syncEntity(T data) {
        apiSyncEntity(data);
        fireStoreSyncEntity(data);
    }

    private void fireStoreSyncEntity(T data) {
        if(!fireStoreSync) return;

        final String reference = data.getReference() == null
                ? Constants.generateReferenceById(data.getId())
                : data.getReference();
        data.setReference(reference);
        data.setSynced(true);

        fireStoreDatabase.addOrUpdate(collectionName, reference, data)
                .addOnSuccessListener(documentReference -> repository.updateOnly(data.getId(), data));
    }

    private void apiSyncEntity(T data) {
        if(!apiStoreSync) return;

        //todo: sync with the api
    }
}
