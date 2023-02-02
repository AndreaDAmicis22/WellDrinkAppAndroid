package com.example.welldrink.model;

public class User {

    private String name;
    private String email;
    private final String id;

    public User(String name, String email, String id){
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }
}
