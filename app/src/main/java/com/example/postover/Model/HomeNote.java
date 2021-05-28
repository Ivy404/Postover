package com.example.postover.Model;

import android.media.Image;
import android.os.Parcel;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.postover.Splash;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeNote extends Note{
    private String text;
    private List<String> images,texts;
    //Falta Content

    public HomeNote(String title){
        this.creationDate = Calendar.getInstance().getTime();
        this.setLastModification();
        this.setTitle(title);
        this.setText("");
        images = new ArrayList<>();
        texts = new ArrayList<>();
    }
    public HomeNote(){}

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> ImageView) {
        this.images = ImageView;
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
