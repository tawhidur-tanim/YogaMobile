package com.grouptwo.dyoga.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.grouptwo.dyoga.data.entities.ClassInstance;

import java.util.List;

@Dao
public interface ClassInstanceDao extends BaseDao<ClassInstance>{

    @Query("select * from ClassInstance limit 1")
    ClassInstance findFirst();

    @Query("select * from ClassInstance")
    List<ClassInstance> findAll();

    @Query("select * from ClassInstance where instructor like :keyword or comments like :keyword")
    List<ClassInstance> findClassInstance(String keyword);

    @Query("select * from ClassInstance where courseId = :courseId")
    List<ClassInstance> findAllByCourseId(int courseId);
}
