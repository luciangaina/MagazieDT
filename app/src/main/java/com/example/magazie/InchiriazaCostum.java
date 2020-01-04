package com.example.magazie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class InchiriazaCostum extends AppCompatActivity {

    private Toolbar toolbar;
    private List<Costum> costumList;
    private ListView listView;
    private MyCustomListAdapter_Inchiriaza adapter_inchiriaza;

    private ArrayList<String> codes = new ArrayList<>();

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inchiriaza_costum);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        toolbar = findViewById(R.id.toolbarInchiriaza);
        toolbar.setTitle("Închiriază costum");

        costumList = new ArrayList<>();

        costumList.add(new Costum("Resita", 10, "M"));
        costumList.add(new Costum("Oltenia", 10, "F"));
        costumList.add(new Costum("Nasaud", 12, "M"));
        costumList.add(new Costum("Moldova", 8, "M"));

        listView = findViewById(R.id.listViewInchiriaza);

        adapter_inchiriaza = new MyCustomListAdapter_Inchiriaza(this, R.layout.inchiriaza_list_item, costumList);

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
                inchiriazaCostum(scannedCode);
            }
        }
    }

    public void inchiriazaCostum(final String scannedCode) {
        DatabaseReference pathMagazie = databaseReference.child("Magazie");

        pathMagazie.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot idCostum : dataSnapshot.getChildren()) {
                    try {
                            if(scannedCode.equals(idCostum.getKey()))
                            {
                                Costum costum = idCostum.getValue(Costum.class);

                                String numeCostum = costum.getNume();
                                int nrCostum = costum.getNumar();
                                String userGender = costum.getGen();

                                costumList.add(new Costum(numeCostum,nrCostum,userGender));
                            }
                    }catch (Exception e) {
                        Toast.makeText(InchiriazaCostum.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
                adapter_inchiriaza.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
