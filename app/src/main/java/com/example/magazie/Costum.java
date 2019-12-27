package com.example.magazie;

public class Costum {
    public String nume;
    public int numar;
    public String gen;

    public Costum() {
    }

    public Costum(String nume, int numar, String gen) {
        this.nume = nume;
        this.numar = numar;
        this.gen = gen;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }
}
