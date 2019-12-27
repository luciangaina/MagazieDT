package com.example.magazie;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Magazie extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazie);

        toolbar = findViewById(R.id.toolbarMagazie);
        toolbar.setTitle("Status Magazie");
    }
}
