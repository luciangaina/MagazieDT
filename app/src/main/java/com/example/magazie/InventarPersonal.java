package com.example.magazie;

import android.os.Bundle;
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

public class InventarPersonal extends AppCompatActivity {

    private Toolbar toolbar;
    private List<CostumInchiriat> costumInchiriatList;
    private ListView listView;
    private MyCustomListAdapter_Inventar adapter_inventar;

    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventar_personal);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        toolbar = findViewById(R.id.toolbarInventar);
        toolbar.setTitle("Inventar personal");

        costumInchiriatList = new ArrayList<>();
        listView = findViewById(R.id.listViewInventar);
        adapter_inventar = new MyCustomListAdapter_Inventar(this, R.layout.inventar_list_item, costumInchiriatList);
        listView.setAdapter(adapter_inventar);

        vizualizareInventar();
    }

    private void vizualizareInventar() {
        String userID = firebaseUser.getUid();
        DatabaseReference pathUser = databaseReference.child("CostumeInchiriate").child(userID);

        pathUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot idCostum : dataSnapshot.getChildren()) {
                    try {
                            CostumInchiriat costumInchiriat = idCostum.getValue(CostumInchiriat.class);

                            String numeCostum = costumInchiriat.getNume();
                            int nrCostum = costumInchiriat.getNumar();
                            String userGender = costumInchiriat.getGen();
                            String timestamp = costumInchiriat.getTimestamp();

                            costumInchiriatList.add(new CostumInchiriat(numeCostum, nrCostum, userGender, timestamp));
                    }catch (Exception e) {
                        Toast.makeText(InventarPersonal.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
                adapter_inventar.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
