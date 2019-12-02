package com.example.magazie;

public class User {
    private String nume;
    private String email;
    private String telefon;
    private String nr_costum;
    private String gen;
    private Boolean admin;

    public User() {

    }

    public User(String nume, String email, String telefon, String nr_costum, String gen, Boolean admin) {
        this.nume = nume;
        this.email = email;
        this.telefon = telefon;
        this.nr_costum = nr_costum;
        this.gen = gen;
        this.admin = admin;
    }
}
