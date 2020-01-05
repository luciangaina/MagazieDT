package com.example.magazie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Magazie extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText searchText;
    private List<Costum> costumList;
    private ListView listView;
    private MyCustomListAdapter_Inchiriaza adapter_magazie;

    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazie);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        toolbar = findViewById(R.id.toolbarMagazie);
        toolbar.setTitle("Status Magazie");

        costumList = new ArrayList<>();
        listView = findViewById(R.id.listViewMagazie);
        adapter_magazie = new MyCustomListAdapter_Inchiriaza(this, R.layout.inchiriaza_list_item, costumList);
        listView.setAdapter(adapter_magazie);

        searchText = findViewById(R.id.editTextSearch);

        vizualizareMagazie();
    }

    private void vizualizareMagazie() {
        DatabaseReference pathMagazie = databaseReference.child("Magazie");

        pathMagazie.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot codCostume : dataSnapshot.getChildren()) {
                    try {
                            Costum costum = codCostume.getValue(Costum.class);

                            String numeCostum = costum.getNume();
                            int nrCostum = costum.getNumar();
                            String userGender = costum.getGen();

                            costumList.add(new Costum(numeCostum, nrCostum, userGender));
                    } catch (Exception e) {
                        Toast.makeText(Magazie.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                adapter_magazie.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onAdaugaCostum(View view) {
        startActivity(new Intent(Magazie.this,AdaugaCostum.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter_magazie.notifyDataSetChanged();
    }
}
