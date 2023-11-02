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
    private final static MutableLiveData<List<ClassInstance>> allClassInstances = new MutableLiveData<>();

    public ClassInstanceViewModel(@NonNull final Application application) {
        super(application);

        this.classInstanceRepository = new ClassInstanceRepository(application);
    }

    public void queryAllByCourseId(int id){
        classInstanceRepository.queryAllByCourseId(id, new Callback<ClassInstance>() {
            @Override
            public void onResult(List<ClassInstance> result) {
                allClassInstances.postValue(result);
            }
        });
    }

    public LiveData<List<ClassInstance>> getClassInstances(){
        return allClassInstances;
    }


    public void add(ClassInstance classInstance){
        classInstanceRepository.add(classInstance);
    }

    public void update(ClassInstance classInstance){
        classInstanceRepository.add(classInstance);
    }

    public void delete(ClassInstance classInstance){
        classInstanceRepository.delete(classInstance);
    }

    public void search(String type){
        classInstanceRepository.search(type, new Callback<ClassInstance>() {
            @Override
            public void onResult(List<ClassInstance> result) {
                allClassInstances.setValue(result);
            }
        });
    }

}
