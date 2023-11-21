package com.grouptwo.dyoga.data.entities;

import android.graphics.Color;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class ClassInstance implements RemoteEntity, Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private LocalDateTime date;
    private String instructor;
    private String room;
    private String comments;
    private long courseId;
    private String reference;
    private boolean deleted;
    private String userId;
    private boolean synced;
    private String color;


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
    public String getColor() {return color;}
    public void setColor(String color) {this.color = color;}
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ClassInstance(long courseId, LocalDateTime date, String instructor, String room, String comments) {
        this.courseId = courseId;
        this.date = date;
        this.instructor = instructor;
        this.room = room;
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String getReference() {
        return reference;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setSynced(boolean synced) {
        this.synced = synced;
    }

    @Override
    public boolean isSynced() {
        return synced;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
