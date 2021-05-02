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
    private String username;
    private String mail;
    private String password;
    //private Image icon;
    private List<ToDoNote> todoList;
    private List<HomeNote> homeNoteList;

    private HashMap<String,List<CalendarNote>> hashCalendar;

    public Client(String name, String password, String mail, String username) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.mail = mail;

        todoList = new ArrayList<>();
        homeNoteList = new ArrayList<>();
        hashCalendar = new HashMap<>();

        List<CalendarNote> calendarNotes = new ArrayList<>();
        ToDoNote note = new ToDoNote("Your First Todo!");
        todoList.add(note);

        HomeNote homeNote = new HomeNote("Your first HomeNote!");
        homeNoteList.add(homeNote);


        CalendarNote calendarNote = new CalendarNote("Second Calendar","Information", Calendar.getInstance().getTime());
        calendarNotes.add(calendarNote);
        calendarNote = new CalendarNote("Second Calendar","Information", Calendar.getInstance().getTime());
        calendarNotes.add(calendarNote);

        hashCalendar.put(calendarNote.getDate().toString().substring(8,10),calendarNotes);

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
