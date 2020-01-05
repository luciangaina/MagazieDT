package com.example.magazie;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class InventarPersonal extends AppCompatActivity {

    private Toolbar toolbar;
    private List<CostumInchiriat> costumInchiriatList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventar_personal);

        toolbar = findViewById(R.id.toolbarInventar);
        toolbar.setTitle("Inventar personal");
    }
}
