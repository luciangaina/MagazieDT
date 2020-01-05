package com.example.magazie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyCustomListAdapter_CostumeUser extends ArrayAdapter<CostumeInchiriateUser> {
    private Context context;
    private int resource;
    private List<CostumeInchiriateUser> costumeInchiriateUserList;

    public MyCustomListAdapter_CostumeUser(@NonNull Context context, int resource, @NonNull List<CostumeInchiriateUser> costumeInchiriateUserList) {
        super(context, resource, costumeInchiriateUserList);

        this.context = context;
        this.resource = resource;
        this.costumeInchiriateUserList = costumeInchiriateUserList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.costum_inchiriat_list_item, null);

        TextView tvNumeCostum = view.findViewById(R.id.tvNumeCostum);
        TextView tvNumarCostum = view.findViewById(R.id.tvNumarCostum);
        TextView tvUserGender = view.findViewById(R.id.tvUserGender);
        TextView tvData = view.findViewById(R.id.tvData);
        TextView tvNumeUser = view.findViewById(R.id.tvUser);

        CostumeInchiriateUser costumeInchiriateUser = costumeInchiriateUserList.get(position);

        tvNumeCostum.setText(costumeInchiriateUser.getNume());
        tvNumarCostum.setText(String.valueOf(costumeInchiriateUser.getNumar()));
        tvUserGender.setText(costumeInchiriateUser.getGen());
        tvData.setText(costumeInchiriateUser.getTimestamp().substring(0,10));
        tvNumeUser.setText(costumeInchiriateUser.getNumeUser());

        return view;
    }
}
