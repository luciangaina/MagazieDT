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

public class MyCustomListAdapter_Inventar extends ArrayAdapter<CostumInchiriat> {

    private Context context;
    private int resource;
    private List<CostumInchiriat> costumInchiriatList;

    public MyCustomListAdapter_Inventar(@NonNull Context context, int resource, @NonNull List<CostumInchiriat> costumInchiriatList) {
        super(context, resource, costumInchiriatList);

        this.context = context;
        this.resource = resource;
        this.costumInchiriatList = costumInchiriatList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.inventar_list_item, null);

        TextView tvNumeCostum = view.findViewById(R.id.tvNumeCostum);
        TextView tvNumarCostum = view.findViewById(R.id.tvNumarCostum);
        TextView tvUserGender = view.findViewById(R.id.tvUserGender);
        TextView tvData = view.findViewById(R.id.tvData);

        CostumInchiriat costumInchiriat = costumInchiriatList.get(position);

        tvNumeCostum.setText(costumInchiriat.getNume());
        tvNumarCostum.setText(String.valueOf(costumInchiriat.getNumar()));
        tvUserGender.setText(costumInchiriat.getGen());
        tvData.setText(costumInchiriat.getTimestamp().substring(0,10));

        return view;
    }
}
