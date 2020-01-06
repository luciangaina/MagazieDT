package com.example.magazie;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

public class PredareCostum extends AppCompatActivity implements LocationListener {

    private static final int MY_LOCATION_PERMISSION = 1;
    private Toolbar toolbar;
    private EditText editTextCod;
    private ListView listView;
    private List<CostumInchiriat> costumInchiriatList;
    private MyCustomListAdapter_Inventar adapter_inventar;

    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<String> codes = new ArrayList<>();

    private LocationManager locationManager;
    private Location location;
    private double longitude, latitude;

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

        //checkLocationPermission();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TO_DO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);
        afisareInventar();
    }

    /*
    private void checkLocationPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TO_DO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            ActivityCompat.requestPermissions(PredareCostum.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_LOCATION_PERMISSION);
        }
        else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_LOCATION_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    //location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                } else {
                    Toast.makeText(PredareCostum.this, "Permisiunea nu a fost acceptata", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
     */

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

                if(checkLocationInArea()) {
                    if (codPrimt.equals(cod)) {
                        for (String codCostum : codes) {
                            pathUser.child(codCostum).removeValue();
                        }
                        finish();
                    } else {
                        Toast.makeText(PredareCostum.this, "Codul introdus nu este corect!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(PredareCostum.this, "Nu te aflii in zona magaziei", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean checkLocationInArea() {
        onLocationChanged(location);
        if((longitude >= 21.2371000 && longitude <= 21.2373000) && (latitude >= 45.7353900 && latitude <= 45.7355500) //locatie acasa
            || (longitude >= 21.2259900 && longitude <= 21.2262500) && (latitude >= 45.7474000 && latitude <= 45.7475000)) //locatie facultate
            return true;
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
