package com.example.magazie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdaugaCostum extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editTextID;
    private EditText editTextNume;
    private EditText editTextNr;
    private EditText editTextGender;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String scannedCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_costum);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        toolbar = findViewById(R.id.toolbarAdaugare);
        toolbar.setTitle("Adaugare costum");

        editTextID = findViewById(R.id.editTextID);
        editTextNume = findViewById(R.id.editTextNume);
        editTextNr = findViewById(R.id.editTextNr);
        editTextGender = findViewById(R.id.editTextGender);
    }

    public void onScannerClicked(View view) {
        Intent intent = new Intent(AdaugaCostum.this, Scanner.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                scannedCode = data.getStringExtra("resultCode");
            }
        }
        editTextID.setText(scannedCode);
    }

    public void onFinishClicked(View view) {
        DatabaseReference pathCostum = databaseReference.child("Magazie").child(scannedCode);

        String numeCostum = editTextNume.getText().toString();
        int nrCostum = Integer.parseInt(editTextNr.getText().toString());
        String userGender = editTextGender.getText().toString();

        Costum costum = new Costum(numeCostum, nrCostum, userGender);

        pathCostum.setValue(costum);

        finish();
    }
}
