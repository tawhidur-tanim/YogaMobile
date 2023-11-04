package com.grouptwo.dyoga.data.repositories;

public interface RemoteRepository<T> {
    void updateOnly(final long id, final T data);
}
