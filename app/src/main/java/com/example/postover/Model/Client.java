package com.example.postover.Model;

import android.media.Image;

public class Client {
    private String name;
    private String username;
    private String mail;
    private String password;
    private Image icon;

    public Client(String name, String password, String mail, String username) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.mail = mail;
    }

    public String getName() {
        return name;
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
