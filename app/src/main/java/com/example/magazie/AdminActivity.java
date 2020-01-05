package com.example.magazie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    private Button bttnLogout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toolbar = findViewById(R.id.toolbarAdmin);
        toolbar.setTitle("Profil administrator");

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
    }

    public void onCreateUser(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, SignUpActivity.class));
            }
        });
    }

    public void onShowMagazie(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, Magazie.class));
            }
        });
    }
}
