package com.example.magazie;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    private EditText parolaNoua;
    private EditText parolaConfirmata;
    private Button buttonParolaSchimbata;
    private Toolbar toolbar;

    private FirebaseUser firebaseUser;
    private String newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        parolaNoua = findViewById(R.id.etNewPassword);
        parolaConfirmata = findViewById(R.id.etConfirmPassword);
        buttonParolaSchimbata = findViewById(R.id.bttnSchimbaParola);
        toolbar = findViewById(R.id.toolbarChangePassword);

        toolbar.setTitle("Schimbă parola");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    private boolean checkPassword() {
        newPassword = parolaNoua.getText().toString();
        String confirmPassword = parolaConfirmata.getText().toString();

        if(newPassword.equals(confirmPassword))
            return true;
        return false;
    }

    public void onChangePasswordClicked(View view) {
        if(checkPassword()) {
            firebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(ChangePassword.this, "Praolă schimbată", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                        Toast.makeText(ChangePassword.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
