package com.grouptwo.dyoga.data.entities;

public interface RemoteEntity {
    long getId();
    void setUserId(String userId);
    String getUserId();
    void setReference(String reference);
    String getReference();
    void setSynced(boolean synced);
    boolean isSynced();
    void setDeleted(boolean deleted);
    boolean isDeleted();
}
