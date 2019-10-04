package com.starixc.adminhans;


import android.content.Intent;
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
public class HomeFragment extends Fragment {

    private ImageView category, products, orders, feedback, users, transactions;
    private View itemView;
    private FragmentTransaction fr;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        itemView= inflater.inflate(R.layout.fragment_home, container, false);
        category = (ImageView) itemView.findViewById(R.id.category);
        products = (ImageView) itemView.findViewById(R.id.products);
        orders = (ImageView) itemView.findViewById(R.id.orders);
        feedback = (ImageView) itemView.findViewById(R.id.feedBack);
        users = (ImageView) itemView.findViewById(R.id.userList);
        transactions = (ImageView) itemView.findViewById(R.id.transactions);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment,new CategoryFragment());
                fr.commit();
            }
        });
        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment,new ProductsFragment());
                fr.commit();
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment,new OrdersFragment());
                fr.commit();
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment,new FeedBackFragment());
                fr.commit();
            }
        });
        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment,new UsersFragment());
                fr.commit();
            }
        });
        transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment,new AddProductFragment());
                fr.commit();
            }
        });

    return itemView;
    }

}
