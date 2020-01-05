package com.example.magazie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class PredareCostum extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editTextCod;
    private ListView listView;
    private List<CostumInchiriat> costumInchiriatList;
    private MyCustomListAdapter_Inventar adapter_inventar;

    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<String> codes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predare_costum);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        toolbar = findViewById(R.id.toolbarPredare);
        toolbar.setTitle("Predare costume");

        editTextCod = findViewById(R.id.editTextCod);

        costumInchiriatList = new ArrayList<>();
        listView = findViewById(R.id.listViewInventar);
        adapter_inventar = new MyCustomListAdapter_Inventar(this, R.layout.inventar_list_item, costumInchiriatList);
        listView.setAdapter(adapter_inventar);

        afisareInventar();
    }

    private void afisareInventar() {
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
                        Toast.makeText(PredareCostum.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
                adapter_inventar.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onScannerClicked(View view) {
        Intent intent = new Intent(PredareCostum.this, Scanner.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String scannedCode = data.getStringExtra("resultCode");
                codes.add(scannedCode);
                predareCostum(scannedCode);
            }
        }
    }

    private void predareCostum(final String scannedCode) {
        String userID = firebaseUser.getUid();
        DatabaseReference pathUser = databaseReference.child("CostumeInchiriate").child(userID);
        
        pathUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot codCostume : dataSnapshot.getChildren()) {
                    try {
                            String idCostum = codCostume.getKey();
                            if(scannedCode.equals(idCostum)) {
                                CostumInchiriat costumInchiriat = codCostume.getValue(CostumInchiriat.class);

                                String numeCostum = costumInchiriat.getNume();
                                int nrCostum = costumInchiriat.getNumar();
                                String userGender = costumInchiriat.getGen();

                                adaugaCostumMagazie(idCostum, numeCostum, nrCostum, userGender);

                                for (CostumInchiriat  toDelete : costumInchiriatList)
                                    if(toDelete.getNume().equals(numeCostum) && toDelete.getNumar() == nrCostum && toDelete.getGen().equals(userGender))
                                        costumInchiriatList.remove(toDelete);
                            }
                    } catch (Exception e) {
                        Toast.makeText(PredareCostum.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
                adapter_inventar.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void adaugaCostumMagazie(final String idCostum, final String numeCostum, final int nrCostum, final String userGender) {
        final DatabaseReference pathMagazie = databaseReference.child("Magazie");

        pathMagazie.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DatabaseReference pathCostum = pathMagazie.child(idCostum);

                Costum costum = new Costum(numeCostum, nrCostum, userGender);
                pathCostum.setValue(costum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onFinishClicked(View view) {
        String userId = firebaseUser.getUid();
        final DatabaseReference pathUser = databaseReference.child("CostumeInchiriate").child(userId);
        DatabaseReference pathCod = databaseReference.child("Cod");
        final String codPrimt = editTextCod.getText().toString();

        pathCod.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String cod = dataSnapshot.getValue(String.class);

                if(codPrimt.equals(cod)) {
                    for (String codCostum : codes) {
                        pathUser.child(codCostum).removeValue();
                    }
                    finish();
                }
                else {
                    Toast.makeText(PredareCostum.this, "Codul introdus nu este corect!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
