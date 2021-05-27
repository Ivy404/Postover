package com.example.postover.Model;

import android.media.Image;
import android.os.Parcel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeNote extends Note{
    private String text;
    private ArrayList<Image> images;
    //Falta Content

    public HomeNote(String title){
        this.creationDate = Calendar.getInstance().getTime();
        this.setLastModification();
        this.setTitle(title);
        this.setText("");
        images = new ArrayList<>();
    }
    public HomeNote(){}
    @Override
    public String getTitle() {
        return this.title;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
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

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void save() {
        //TODO
    }


}
