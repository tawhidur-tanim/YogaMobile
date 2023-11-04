package com.grouptwo.dyoga.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.grouptwo.dyoga.data.entities.ClassInstance;

import java.util.List;

@Dao
public interface ClassInstanceDao extends BaseDao<ClassInstance>{

    @Query("select * from ClassInstance where deleted = 0 and (" +
            "instructor like '%' || :keyword || '%'" +
            " or comments like '%' || :keyword || '%')")
    List<ClassInstance> findClassInstance(String keyword);

    @Query("select * from ClassInstance where courseId = :courseId and deleted = 0")
    List<ClassInstance> findAllByCourseId(long courseId);
}
