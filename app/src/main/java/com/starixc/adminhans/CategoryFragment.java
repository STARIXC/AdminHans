package com.starixc.adminhans;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements View.OnClickListener{

    private ImageView whisky,wine,spirits,beers,champagne,chasers;
    private View categoryView;
    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        categoryView= inflater.inflate(R.layout.fragment_category, container, false);

        whisky=(ImageView) categoryView.findViewById(R.id.whisky);
        wine=(ImageView) categoryView.findViewById(R.id.wine);
        spirits =(ImageView) categoryView.findViewById(R.id.spirits);
        beers=(ImageView) categoryView.findViewById(R.id.beers);
        champagne=(ImageView) categoryView.findViewById(R.id.champagne);
        chasers=(ImageView) categoryView.findViewById(R.id.chasers);


        whisky.setOnClickListener(this);

        wine.setOnClickListener(this);
        spirits.setOnClickListener(this);
        beers.setOnClickListener(this);
        champagne.setOnClickListener(this);
        chasers.setOnClickListener(this);

        return  categoryView;

    }

    @Override
    public void onClick(View view) {


    }
}
