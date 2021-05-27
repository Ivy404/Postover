package com.example.postover.Model;

import android.os.Parcel;

import java.util.Calendar;
import java.util.Date;

public class ToDoNote extends Note{

    private boolean completed;

    public ToDoNote(String title){
        this.creationDate = Calendar.getInstance().getTime();
        this.setLastModification();
        this.setTitle(title);
        this.completed = false;
    }
    public ToDoNote(){}
    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Date getLastModification() {
        return this.lastModification;
    }

    @Override
    public void setLastModification() {
        this.lastModification = Calendar.getInstance().getTime();
    }

    @Override
    public Date getCreationDate() {
        return this.creationDate;
    }

    @Override
    public void save() {
    //TODO
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


}
