package com.example.magazie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class InchiriazaCostum extends AppCompatActivity {

    private Toolbar toolbar;
    private List<Costum> costumList;
    ListView listView;

    private ArrayList<String> codes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inchiriaza_costum);

        toolbar = findViewById(R.id.toolbarInchiriaza);
        toolbar.setTitle("Închiriază costum");

        costumList = new ArrayList<>();

        costumList.add(new Costum("Resita", 10, "M"));
        costumList.add(new Costum("Oltenia", 10, "F"));
        costumList.add(new Costum("Nasaud", 12, "M"));
        costumList.add(new Costum("Moldova", 8, "M"));

        listView = findViewById(R.id.listViewInchiriaza);

        MyCustomListAdapter_Inchiriaza adapter_inchiriaza = new MyCustomListAdapter_Inchiriaza(this, R.layout.inchiriaza_list_item, costumList);

        listView.setAdapter(adapter_inchiriaza);
    }

    public void onScannerClicked(View view) {
        Intent intent = new Intent(InchiriazaCostum.this, Scanner.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String scannedCode = data.getStringExtra("resultCode");
                codes.add(scannedCode);
            }
        }
    }
}
