package com.example.postover.Model;

import java.util.Date;

public abstract class Note {
    //Attributes
    String title;
    Date lastModification;
    Date creationDate;

    //Abstract Methods
    public abstract String getTitle();
    public abstract void setTitle(String title);
    public abstract Date getLastModification();
    public abstract void setLastModification(Date lastModification);
    public abstract Date getCreationDate();
    public abstract void save();
}
