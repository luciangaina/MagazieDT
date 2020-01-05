package com.example.magazie;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CostumeUsers extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText searchText;
    private List<CostumeInchiriateUser> costumeInchiriateUserList;
    private ListView listView;
    private MyCustomListAdapter_CostumeUser adapter_costumeUser;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costume_users);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        toolbar = findViewById(R.id.toolbarCostumeUser);
        toolbar.setTitle("Costume inchiriate");

        costumeInchiriateUserList = new ArrayList<>();
        listView = findViewById(R.id.listViewCostumeUser);
        adapter_costumeUser = new MyCustomListAdapter_CostumeUser(this, R.layout.costum_inchiriat_list_item, costumeInchiriateUserList);
        listView.setAdapter(adapter_costumeUser);

        vizualizareCostumeInchiriate();
    }

    private void vizualizareCostumeInchiriate() {
        final DatabaseReference pathCostume = databaseReference.child("CostumeInchiriate");
        DatabaseReference pathUser = databaseReference.child("Users");

        pathUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    try {
                            final String userId = ds.getKey();
                            final String userName = ds.getValue(User.class).getNume(); //get userId and userName from darabase

                            pathCostume.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot users : dataSnapshot.getChildren()) {
                                        try {
                                                if(userId.equals(users.getKey())) { //daca gasim userId in database (in nodul/tablea "CostumeInchiriate")
                                                    for (DataSnapshot costum : users.getChildren()) { //afisam toate costumele pe care le-a inchiriat acel user
                                                        try {
                                                                CostumInchiriat costumInchiriat = costum.getValue(CostumInchiriat.class);

                                                                String numeCostum = costumInchiriat.getNume();
                                                                int nrCostum = costumInchiriat.getNumar();
                                                                String userGender = costumInchiriat.getGen();
                                                                String timestamp = costumInchiriat.getTimestamp();

                                                                costumeInchiriateUserList.add(new CostumeInchiriateUser(numeCostum, nrCostum, userGender, timestamp, userName));
                                                        } catch (Exception e) {
                                                            Toast.makeText(CostumeUsers.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                    adapter_costumeUser.notifyDataSetChanged();
                                                }
                                        }catch (Exception e) {
                                            Toast.makeText(CostumeUsers.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                    }catch (Exception e){
                        Toast.makeText(CostumeUsers.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
