package com.grouptwo.dyoga.data.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.grouptwo.dyoga.data.entities.ClassInstance;
import com.grouptwo.dyoga.data.repositories.ClassInstanceRepository;
import com.grouptwo.dyoga.interfaces.Callback;

import java.util.List;

public class ClassInstanceViewModel extends AndroidViewModel {

    private final ClassInstanceRepository classInstanceRepository;
    private final MutableLiveData<List<ClassInstance>> allClassInstances;

    public ClassInstanceViewModel(@NonNull final Application application) {
        super(application);

        this.classInstanceRepository = new ClassInstanceRepository(application);
        this.allClassInstances = new MutableLiveData<>();
    }

    public LiveData<List<ClassInstance>> findAll(){
        classInstanceRepository.findAll(new Callback<ClassInstance>() {
            @Override
            public void onResult(ClassInstance result) {

            }

            @Override
            public void onResult(List<ClassInstance> result) {
                allClassInstances.setValue(result);
            }
        });

        return allClassInstances;
    }


    public void add(ClassInstance classInstance){
        classInstanceRepository.add(classInstance);
    }

    public void add(List<ClassInstance> classInstances){
        classInstanceRepository.add(classInstances);
    }

    public void delete(ClassInstance classInstance){
        classInstanceRepository.delete(classInstance);
    }

    public void search(String type){
        classInstanceRepository.search(type, new Callback<ClassInstance>() {
            @Override
            public void onResult(ClassInstance result) {
            }

            @Override
            public void onResult(List<ClassInstance> result) {
                allClassInstances.setValue(result);
            }
        });
    }

}
