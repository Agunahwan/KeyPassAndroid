package com.agunahwan.keypass.entity;

/**
 * Created by InspiroPC on 13/09/2015.
 */
public class PasswordSaved {
    private int id;
    private String nama;
    private String username;
    private String password;

    public PasswordSaved() {
    }

    public PasswordSaved(int id, String nama, String username, String password) {
        super();
        this.id = id;
        this.nama = nama;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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
