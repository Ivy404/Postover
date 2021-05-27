package com.example.postover.Model;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public abstract class Note implements Serializable {
    //Attributes
    String title;
    Date lastModification;
    Date creationDate;

    //Abstract Methods
    public abstract String getTitle();
    public abstract void setTitle(String title);
    public abstract Date getLastModification();
    public abstract void setLastModification();
    public abstract Date getCreationDate();
    public abstract void save();
}
