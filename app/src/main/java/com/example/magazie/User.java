package com.example.magazie;

public class User {
    public String nume;
    public String email;
    public String telefon;
    public String nr_costum;
    public String gen;
    public String admin;

    public User() {

    }

    public User(String nume, String email, String telefon, String nr_costum, String gen, String admin) {
        this.nume = nume;
        this.email = email;
        this.telefon = telefon;
        this.nr_costum = nr_costum;
        this.gen = gen;
        this.admin = admin;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
