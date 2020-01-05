package com.example.magazie;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private EditText email;
    private EditText password;
    private EditText nume;
    private EditText telefon;
    private EditText nr_costum;
    private int numar_costum_int;
    private Button signup;
    private CheckBox checkBox;
    private RadioButton radioButton_barbat;
    private RadioButton radioButton_femeie;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String gen;
    private String isAdmin = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar = findViewById(R.id.toolbarSignup);
        progressBar = findViewById(R.id.progressBarSignup);
        email = findViewById(R.id.emailSignup);
        password = findViewById(R.id.passwordSignup);
        nume = findViewById(R.id.etNume);
        telefon = findViewById(R.id.etTelefon);
        nr_costum = findViewById(R.id.etCostum);
        signup = findViewById(R.id.buttonSignup);
        checkBox = findViewById(R.id.checkBox);
        radioButton_barbat = findViewById(R.id.barbat);
        radioButton_femeie = findViewById(R.id.femeie);

        toolbar.setTitle("Creare Utilizator");

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            numar_costum_int = Integer.parseInt(nr_costum.getText().toString());
                            User user = new User(
                                    nume.getText().toString(),
                                    email.getText().toString(),
                                    telefon.getText().toString(),
                                    numar_costum_int,
                                    gen,
                                    isAdmin);

                            databaseReference.child("Users").child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                Toast.makeText(SignUpActivity.this, "Utilizator creat",Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                            nume.setText("");
                            email.setText("");
                            password.setText("");
                            telefon.setText("");
                            nr_costum.setText("");
                            radioButton_barbat.setChecked(false);
                            radioButton_femeie.setChecked(false);
                            checkBox.setChecked(false);
                        } else {
                            Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    public void onRadioButtonClicked (View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.barbat:
                if(checked)
                    gen = "M";
                break;
            case R.id.femeie:
                if(checked)
                    gen = "F";
                break;
        }
    }

    public void onCheckBoxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()){
            case R.id.checkBox:
                if(checked)
                    isAdmin = "true";
                else
                    isAdmin = "false";
                break;
        }
    }
}
