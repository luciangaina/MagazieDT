package com.example.magazie;

public class CostumInchiriat extends Costum {

    public String timestamp;

    public CostumInchiriat() {
    }

    public CostumInchiriat(String nume, int numar, String gen, String timestamp) {
        super(nume, numar, gen);
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
