package com.example.magazie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class InchiriazaCostum extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inchiriaza_costum);

        toolbar = findViewById(R.id.toolbarInchiriaza);
        toolbar.setTitle("Închiriază costum");
    }

    public void onScannerClicked(View view) {
        Intent intent = new Intent(InchiriazaCostum.this, Scanner.class);
        startActivity(intent);
    }
}
