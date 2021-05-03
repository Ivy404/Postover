package com.example.postover.Model;

import android.media.Image;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.postover.R;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {
    private String name;
    private String mail;
    //private Image icon;
    private List<ToDoNote> todoList;
    private List<HomeNote> homeNoteList;

    private HashMap<String,List<CalendarNote>> hashCalendar;

    public Client(String name, String mail) {
        this.name = name;
        this.mail = mail;
        todoList = new ArrayList<>();
        homeNoteList = new ArrayList<>();
        hashCalendar = new HashMap<>();

        List<CalendarNote> calendarNotes = new ArrayList<>();
        ToDoNote note = new ToDoNote("Your First Todo!");
        todoList.add(note);

        HomeNote homeNote = new HomeNote("Your first HomeNote!");
        homeNoteList.add(homeNote);


        CalendarNote calendarNote = new CalendarNote("First Calendar","Information", Calendar.getInstance().getTime());
        calendarNotes.add(calendarNote);

        hashCalendar.put(Long.toString(calendarNote.getDate().getTime()),calendarNotes);

    }
    public Client(){}

    public String getName() {
        return name;
    }

    public List<HomeNote> getHomeNoteList() {
        return homeNoteList;
    }

    public void setHomeNoteList(List<HomeNote> homeNoteList) {
        this.homeNoteList = homeNoteList;
    }

    public List<ToDoNote> getTodoList() {
        return todoList;
    }


    public HashMap<String, List<CalendarNote>> getHashCalendar() {
        return hashCalendar;
    }

    public void setHashCalendar(HashMap<String, List<CalendarNote>> hashCalendar) {
        this.hashCalendar = hashCalendar;
    }

    public void setTodoList(List<ToDoNote> todoList) {
        this.todoList = todoList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }



}
