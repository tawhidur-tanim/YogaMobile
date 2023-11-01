package com.grouptwo.dyoga.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.grouptwo.dyoga.data.entities.Course;

import java.util.List;

@Dao
public interface CourseDao extends BaseDao<Course>{

    @Query("select * from course limit 1")
    Course findFirst();

    @Query("select * from course")
    List<Course> getAllCourses();

    @Query("select * from course where id=:id")
    Course getCourseById(int id);

    @Query("select * from course  where courseType like '%' || :type || '%'")
    List<Course> searchCourses(String type);
}
