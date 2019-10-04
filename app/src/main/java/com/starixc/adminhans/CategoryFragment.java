package com.starixc.adminhans;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    private ImageView whisky,wine,spirits,beers,champagne,chasers;
    private View categoryView;
    private FragmentTransaction fr;
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


        whisky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment,new AddProductFragment());
                fr.commit();

            }
        });

        wine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment,new AddProductFragment());
                fr.commit();
            }
        });
        spirits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment,new AddProductFragment());
                fr.commit();
            }
        });
        beers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment,new AddProductFragment());
                fr.commit();
            }
        });
        champagne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment,new AddProductFragment());
                fr.commit();
            }
        });
        chasers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment,new AddProductFragment());
                fr.commit();
            }
        });
return categoryView;
    }


}
