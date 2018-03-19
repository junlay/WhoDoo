package com.example.whodoo.DB;

/**
 * Created by Junnan on 2018-03-19.
 */

public class User {

    String username;
    String password;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
