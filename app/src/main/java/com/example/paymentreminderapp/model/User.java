package com.example.paymentreminderapp.model;

public class User {

    String id;
    String idToken;
    String displayName;
    String email;

    public User() {}

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

    public User(String id, String idToken, String displayName, String email) {
        this.id = id;
        this.idToken = idToken;
        this.displayName = displayName;
        this.email = email;
    }
}
