package com.example.postover.Model;

import java.util.Calendar;
import java.util.Date;

public class ToDoNote extends Note{

    private boolean completed;
    private boolean resetToDo; // PARA EL RESET CREO QUE SERIA MEJOR HACER QUE CUANDO SE AVANZA DE DIA SE HAGA CLEAR DE LO QUE HABIA

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
}
