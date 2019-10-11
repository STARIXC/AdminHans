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

    private ImageView mixers,beers,brandy_cognac,champagne,gin,liqour,others,rum,tequila,vodka,whisky,wine;
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
        mixers=(ImageView) categoryView.findViewById(R.id.mixers);
        beers=(ImageView) categoryView.findViewById(R.id.beers);
        brandy_cognac=(ImageView) categoryView.findViewById(R.id.brandy_cognac);
        champagne=(ImageView) categoryView.findViewById(R.id.champagne);
        gin=(ImageView) categoryView.findViewById(R.id.gin);
        liqour =(ImageView) categoryView.findViewById(R.id.spirits);
        others =(ImageView) categoryView.findViewById(R.id.others);
        rum=(ImageView) categoryView.findViewById(R.id.ram);
        tequila=(ImageView) categoryView.findViewById(R.id.tequila);
        vodka=(ImageView) categoryView.findViewById(R.id.vodka);
        whisky=(ImageView) categoryView.findViewById(R.id.whisky);
        wine=(ImageView) categoryView.findViewById(R.id.wine);






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
        liqour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","liquor");
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
        rum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","rum");
                fm=getActivity().getSupportFragmentManager();
                ft =fm.beginTransaction();
                AddProductFragment addProductFragment= new AddProductFragment();
                addProductFragment.setArguments(bundle);
                ft.replace(R.id.fragment,addProductFragment) .addToBackStack(null);
                ft.commit();
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","others");
                fm=getActivity().getSupportFragmentManager();
                ft =fm.beginTransaction();
                AddProductFragment addProductFragment= new AddProductFragment();
                addProductFragment.setArguments(bundle);
                ft.replace(R.id.fragment,addProductFragment) .addToBackStack(null);
                ft.commit();
            }
        });
        gin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","gin");
                fm=getActivity().getSupportFragmentManager();
                ft =fm.beginTransaction();
                AddProductFragment addProductFragment= new AddProductFragment();
                addProductFragment.setArguments(bundle);
                ft.replace(R.id.fragment,addProductFragment) .addToBackStack(null);
                ft.commit();
            }
        });
        mixers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","mixers");
                fm=getActivity().getSupportFragmentManager();
                ft =fm.beginTransaction();
                AddProductFragment addProductFragment= new AddProductFragment();
                addProductFragment.setArguments(bundle);
                ft.replace(R.id.fragment,addProductFragment) .addToBackStack(null);
                ft.commit();
            }
        });
        brandy_cognac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","brandy & cognac");
                fm=getActivity().getSupportFragmentManager();
                ft =fm.beginTransaction();
                AddProductFragment addProductFragment= new AddProductFragment();
                addProductFragment.setArguments(bundle);
                ft.replace(R.id.fragment,addProductFragment) .addToBackStack(null);
                ft.commit();
            }
        });
        tequila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","tequila");
                fm=getActivity().getSupportFragmentManager();
                ft =fm.beginTransaction();
                AddProductFragment addProductFragment= new AddProductFragment();
                addProductFragment.setArguments(bundle);
                ft.replace(R.id.fragment,addProductFragment) .addToBackStack(null);
                ft.commit();
            }
        });
        vodka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","vodka");
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
