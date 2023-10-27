package com.grouptwo.dyoga.data.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Course {
    public Course(String courseName, LocalDateTime courseDate, double price) {
        this.courseName = courseName;
        this.courseDate = courseDate;
        this.price = price;
    }

    private String courseName;

    private LocalDateTime courseDate;

    private double price;




    public LocalDateTime getCourseDate() {
        return courseDate;
    }

    public double getPrice() {
        return price;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseDate(LocalDateTime courseDate) {
        this.courseDate = courseDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
