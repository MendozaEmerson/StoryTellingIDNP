package com.example.loginsample2.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.loginsample2.AdapterRecyclerView;
import com.example.loginsample2.R;

import java.util.ArrayList;
import java.util.List;

public class CuadrosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private AdapterRecyclerView adapterRecyclerView;
    private List<AdapterRecyclerView.Item> items;

    public CuadrosFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CuadrosFragment newInstance(String param1, String param2) {
        CuadrosFragment fragment = new CuadrosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_cuadros, container, false);

        SearchView searchView= view.findViewById(R.id.searchView);
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //ITEMS DE EJEMPLO
        items= new ArrayList<>();
        items.add(new AdapterRecyclerView.Item(1,"Carpintero de Nidos","Sala N° X", "Daniel Gallegos Esquivias"));
        items.add(new AdapterRecyclerView.Item(1,"Carpintero de Nidos","Sala N° X", "Daniel Gallegos Esquivias"));
        items.add(new AdapterRecyclerView.Item(1,"Carpintero de Nidos","Sala N° X", "Daniel Gallegos Esquivias"));
        items.add(new AdapterRecyclerView.Item(1,"Carpintero de Nidos","Sala N° X", "Daniel Gallegos Esquivias"));
        items.add(new AdapterRecyclerView.Item(1,"Carpintero de Nidos","Sala N° X", "Daniel Gallegos Esquivias"));
        items.add(new AdapterRecyclerView.Item(1,"Carpintero de Nidos","Sala N° X", "Daniel Gallegos Esquivias"));
        items.add(new AdapterRecyclerView.Item(1,"Carpintero de Nidos","Sala N° X", "Daniel Gallegos Esquivias"));
        items.add(new AdapterRecyclerView.Item(1,"Carpintero de Nidos","Sala N° X", "Daniel Gallegos Esquivias"));


        adapterRecyclerView=new AdapterRecyclerView(items);
        recyclerView.setAdapter(adapterRecyclerView);

        return view;
    }
}