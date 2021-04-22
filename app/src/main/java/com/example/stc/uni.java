package com.example.stc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class uni extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<datamodel> dataholder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_uni, container, false);
        recyclerView = view.findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataholder = new ArrayList<>();
        datamodel ob1 = new datamodel(R.drawable.sup_logo,"Jazz","MAD");
        dataholder.add(ob1);
        datamodel ob2 = new datamodel(R.drawable.sup_logo,"Warid","OOAD");
        dataholder.add(ob2);
        datamodel ob3 = new datamodel(R.drawable.sup_logo,"Ufone","SE");
        dataholder.add(ob3);
        datamodel ob4 = new datamodel(R.drawable.pulogo,"Zong","DIP");
        dataholder.add(ob4);
        datamodel ob5 = new datamodel(R.drawable.pulogo,"Telenor","SDA");
        dataholder.add(ob5);
        recyclerView.setAdapter(new myadapter(dataholder));
        return view;
    }
}