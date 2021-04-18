package com.example.postover.Model;

import java.util.Calendar;
import java.util.Date;

public class CalendarNote extends Note {

    private String subtitle;
    private boolean notifications;
    private Date date;

    public CalendarNote(String title, String subtitle, Date date) {
        this.creationDate = Calendar.getInstance().getTime();
        this.setLastModification();
        this.setTitle(title);
        this.setSubtitle(subtitle);
        this.setDate(date);
        this.notifications = false;
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

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public void save() {
        //TODO
    }

    public void switchNotifications() {
        this.notifications = !this.notifications;
    }
}
