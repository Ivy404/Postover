package com.example.postover.Model;

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

    public void switchCompleted(){
        this.completed = !this.completed;
    }

    public boolean getCompleted(){
        return this.completed;
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
