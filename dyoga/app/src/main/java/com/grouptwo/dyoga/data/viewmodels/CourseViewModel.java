package com.grouptwo.dyoga.data.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.grouptwo.dyoga.data.Entities.Course;
import com.grouptwo.dyoga.data.repositories.CourseRepository;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    private CourseRepository courseRepository;

    private MutableLiveData<String> searchQuery = new MutableLiveData<>();
    private LiveData<List<Course>> allCourses = Transformations.switchMap(searchQuery,
            query -> courseRepository.searchCourses(query));


    public CourseViewModel(@NonNull Application application) {
        super(application);
        this.courseRepository = new CourseRepository(application);


    }

    public LiveData<List<Course>> getAllCourses(){
        allCourses = courseRepository.getAllCourses();
        return allCourses;
    }


    public void addCourse(Course course){

        courseRepository.addCourse(course);
    }

    public void addCourseList(List<Course> courses){

        courseRepository.addCourseList(courses);
    }

    public void deleteCourse(Course course){

        courseRepository.deleteCourse(course);
    }

    public void searchCourse(String type){

        Log.d("from veiw model", type);
        searchQuery.setValue(type);

        LiveData<List<Course>> liveData =  courseRepository.searchCourses(type);

        if(liveData.getValue() == null){
            Log.d("from veiw model 2", "nothing" + "");
        }

    }

}
