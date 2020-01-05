package com.example.magazie;

public class CostumeInchiriateUser extends CostumInchiriat {

    private String numeUser;

    public CostumeInchiriateUser() {
    }

    public CostumeInchiriateUser(String nume, int numar, String gen, String timestamp, String numeUser) {
        super(nume, numar, gen, timestamp);
        this.numeUser = numeUser;
    }

    public String getNumeUser() {
        return numeUser;
    }
}
