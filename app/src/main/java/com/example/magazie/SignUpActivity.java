package com.example.magazie;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private EditText email;
    private EditText password;
    private Button signup;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar = findViewById(R.id.toolbarSignup);
        progressBar = findViewById(R.id.progressBarSignup);
        email = findViewById(R.id.emailSignup);
        password = findViewById(R.id.passwordSignup);
        signup = findViewById(R.id.buttonSignup);

        toolbar.setTitle("Creare Utilizator");

        firebaseAuth = FirebaseAuth.getInstance();

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
                            Toast.makeText(SignUpActivity.this, "Utilizator creat",Toast.LENGTH_LONG).show();
                            email.setText("");
                            password.setText("");
                        } else {
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
