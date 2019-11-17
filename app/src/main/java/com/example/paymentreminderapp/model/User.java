package com.example.paymentreminderapp.model;

public class User {

    private String id;
    private String idToken;
    private String displayName;
    private String email;
    private String username;
    private String password;

    public User() {}

    public User(String id, String idToken, String displayName, String email, String username, String password) {
        this.id = id;
        this.idToken = idToken;
        this.displayName = displayName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
