package com.grouptwo.dyoga.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BaseDao <T>{

    @Insert
    public void insert(T obj);

    @Insert
    public void insertList(List<T> obj);

    @Update
    public void update(T obj);

    @Delete
    public void delete(T obj);
}
