package com.example.postover.Model;

import android.media.Image;

import com.example.postover.R;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
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

    public Client(String name, String password, String mail, String username) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.mail = mail;
        todoList = new ArrayList<>();
        ToDoNote note = new ToDoNote("G");
        todoList.add(note);

    }
    public Client(){}
    public String getName() {
        return name;
    }



    public List<ToDoNote> getTodoList() {
        return todoList;
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
