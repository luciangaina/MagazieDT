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

public class MyCustomListAdapter_Inchiriaza extends ArrayAdapter<Costum> {

    private Context context;
    private int resource;
    private List<Costum> costumList;

    public MyCustomListAdapter_Inchiriaza(@NonNull Context context, int resource, @NonNull List<Costum> costumList) {
        super(context, resource, costumList);

        this.context = context;
        this.resource = resource;
        this.costumList = costumList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.inchiriaza_list_item, null);

        TextView tvNumeCostum = view.findViewById(R.id.tvNumeCostum);
        TextView tvNumarCostum = view.findViewById(R.id.tvNumarCostum);
        TextView tvUserGender = view.findViewById(R.id.tvUserGender);

        Costum costum = costumList.get(position);

        tvNumeCostum.setText(costum.getNume());
        tvNumarCostum.setText(String.valueOf(costum.getNumar()));
        tvUserGender.setText(costum.getGen());

        return view;
    }
}
