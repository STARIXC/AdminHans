package com.starixc.adminhans;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
    private FragmentManager fm;
    private FragmentTransaction ft;
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
                Bundle bundle = new Bundle();
                bundle.putString("category","whisky");
                fm=getActivity().getSupportFragmentManager();
                ft =fm.beginTransaction();
                AddProductFragment addProductFragment= new AddProductFragment();
                addProductFragment.setArguments(bundle);
                ft.replace(R.id.fragment,addProductFragment) .addToBackStack(null);
                ft.commit();

            }
        });

        wine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","wine");
                fm=getActivity().getSupportFragmentManager();
                ft =fm.beginTransaction();
                AddProductFragment addProductFragment= new AddProductFragment();
                addProductFragment.setArguments(bundle);
                ft.replace(R.id.fragment,addProductFragment) .addToBackStack(null);
                ft.commit();
            }
        });
        spirits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","spirits");
                fm=getActivity().getSupportFragmentManager();
                ft =fm.beginTransaction();
                AddProductFragment addProductFragment= new AddProductFragment();
                addProductFragment.setArguments(bundle);
                ft.replace(R.id.fragment,addProductFragment) .addToBackStack(null);
                ft.commit();
            }
        });
        beers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","beers");
                fm=getActivity().getSupportFragmentManager();
                ft =fm.beginTransaction();
                AddProductFragment addProductFragment= new AddProductFragment();
                addProductFragment.setArguments(bundle);
                ft.replace(R.id.fragment,addProductFragment) .addToBackStack(null);
                ft.commit();
            }
        });
        champagne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","champagne");
                fm=getActivity().getSupportFragmentManager();
                ft =fm.beginTransaction();
                AddProductFragment addProductFragment= new AddProductFragment();
                addProductFragment.setArguments(bundle);
                ft.replace(R.id.fragment,addProductFragment) .addToBackStack(null);
                ft.commit();
            }
        });
        chasers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","chasers");
                fm=getActivity().getSupportFragmentManager();
                ft =fm.beginTransaction();
                AddProductFragment addProductFragment= new AddProductFragment();
                addProductFragment.setArguments(bundle);
                ft.replace(R.id.fragment,addProductFragment) .addToBackStack(null);
                ft.commit();
            }
        });
return categoryView;
    }


}
