package com.example.magazie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class AdminActivity extends AppCompatActivity {

    private Button bttnLogout;
    private Toolbar toolbar;
    private TextView tvValueCod;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        toolbar = findViewById(R.id.toolbarAdmin);
        toolbar.setTitle("Profil administrator");

        tvValueCod = findViewById(R.id.tvValueCod);

        bttnLogout = findViewById(R.id.buttonLogout);
        bttnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        afiseazaCod();
    }

    private void afiseazaCod() {
        DatabaseReference pathChild = databaseReference.child("Cod");

        pathChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String cod = dataSnapshot.getValue(String.class);
                tvValueCod.setText(cod);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onCreateUser(View view) {
        startActivity(new Intent(AdminActivity.this, SignUpActivity.class));
    }

    public void onShowMagazie(View view) {
        startActivity(new Intent(AdminActivity.this, Magazie.class));
    }

    public void onCostumeInchiriate(View view) {
        startActivity(new Intent(AdminActivity.this, CostumeUsers.class));
    }

    public void onGenereazaCod(View view) {
        Random random = new Random();
        String cod = String.format("%04d", random.nextInt(10000));
        tvValueCod.setText(cod);

        //add code to database
        databaseReference.child("Cod").setValue(cod);
    }
}
