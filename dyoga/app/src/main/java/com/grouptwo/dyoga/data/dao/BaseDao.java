package com.grouptwo.dyoga.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BaseDao <T>{

    @Insert
    long insert(T obj);

    @Insert
    void insertList(List<T> obj);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(T obj);
}
