package com.grouptwo.dyoga.data;

import com.grouptwo.dyoga.data.entities.ClassInstance;
import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.data.entities.RemoteEntity;

public class GlobalClassInstance implements RemoteEntity {
    private final Course course;
    private final ClassInstance classInstance;
    private boolean deleted;

    public GlobalClassInstance(Course course, ClassInstance classInstance) {
        this.course = course;
        this.classInstance = classInstance;
    }

    public Course getCourse() {
        return course;
    }

    public ClassInstance getClassInstance() {
        return classInstance;
    }

    @Override
    public long getId() {
        return classInstance.getId();
    }

    @Override
    public void setUserId(String userId) {
        classInstance.setUserId(userId);
    }

    @Override
    public String getUserId() {
        return classInstance.getUserId();
    }

    @Override
    public void setReference(String reference) {
        classInstance.setReference(reference);
    }

    @Override
    public String getReference() {
        return classInstance.getReference();
    }

    @Override
    public void setSynced(boolean synced) {
        classInstance.setSynced(true);
    }

    @Override
    public boolean isSynced() {
        return classInstance.isSynced();
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }
}
