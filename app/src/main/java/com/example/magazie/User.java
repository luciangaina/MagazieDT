package com.example.magazie;

public class User {
    public String nume;
    public String email;
    public String telefon;
    public int nr_costum;
    public String gen;
    public String admin;

    public User() {
    }

    public User(String nume, String email, String telefon, int nr_costum, String gen, String admin) {
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

    public int getNr_costum() {
        return nr_costum;
    }

    public void setNr_costum(int nr_costum) {
        this.nr_costum = nr_costum;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
