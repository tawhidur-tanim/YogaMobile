package com.grouptwo.dyoga.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.grouptwo.dyoga.R;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Course implements RemoteEntity, Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String type;
    private LocalDateTime time;
    private double price;
    private int capacity;
    private int duration;
    private String dayOfWeek;
    private String description;
    private String reference;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Course(final String type, final LocalDateTime time, final double price,
                  final int capacity, final int duration, final String description, final String dayOfWeek) {
        this.type = type;
        this.time = time;
        this.price = price;
        this.capacity = capacity;
        this.duration = duration;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTime() {
        return time;
    }


    public String getTimeText() {
        return DateTimeFormatter.ofPattern("hh:mm a").format(getTime());
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
    }

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

    public String getDayOfWeek() {
        return dayOfWeek;
    }


    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String getReference() {
        return reference;
    }

}
