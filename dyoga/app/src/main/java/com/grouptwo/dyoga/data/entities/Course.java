package com.grouptwo.dyoga.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity
public class Course {

    public Course(String courseType, LocalDateTime courseDate, double price, int capacity, int duration, String description) {
        this.courseType = courseType;
        this.courseDate = courseDate;
        this.price = price;
        this.capacity = capacity;
        this.duration = duration;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String courseType;

    private LocalDateTime courseDate;

    private double price;
    private int capacity;

    private int duration;



    private String description;


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




    public LocalDateTime getCourseDate() {
        return courseDate;
    }

    public double getPrice() {
        return price;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public void setCourseDate(LocalDateTime courseDate) {
        this.courseDate = courseDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
