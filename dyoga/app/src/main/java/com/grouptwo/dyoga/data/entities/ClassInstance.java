package com.grouptwo.dyoga.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity
public class ClassInstance {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private LocalDateTime date;

    private String instructor;

    private String room;

    private String comments;

    public ClassInstance(LocalDateTime date, String instructor, String room, String comments) {
        this.date = date;
        this.instructor = instructor;
        this.room = room;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
