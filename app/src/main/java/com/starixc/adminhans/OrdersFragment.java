package com.starixc.adminhans;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.starixc.adminhans.Adapters.OrderAdapter;
import com.starixc.adminhans.Model.Order;
import com.starixc.adminhans.viewHolder.OrderViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {
    private View ordersView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private RecyclerView ordersList;
    private OrderAdapter orderAdapter;

   // private OrderViewHolder orderViewHolder;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ordersRef = db.collection("Orders");
    private TextView txtTotalAmount,tvmsg;
  // private DatabaseReference ordersRef;

    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ordersView= inflater.inflate(R.layout.fragment_orders, container, false);
//        ordersList =ordersView.findViewById(R.id.order_list_recycler);
//        ordersList.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(getActivity());
       // ordersList.setLayoutManager(layoutManager);

        loadOrderList();

        return  ordersView;

    }

    private void loadOrderList() {
        Query query = ordersRef.orderBy("orderNo", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Order> options = new FirestoreRecyclerOptions.Builder<Order>()
                .setQuery(query,Order.class)
                .build();
        orderAdapter = new OrderAdapter(options);
        ordersList = ordersView.findViewById(R.id.order_list_recycler);
        ordersList.setHasFixedSize(true);
        ordersList.setLayoutManager(new LinearLayoutManager(getContext()));
        ordersList.setAdapter(orderAdapter);





    }

    @Override
    public void onStart() {
        super.onStart();

    orderAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        orderAdapter.stopListening();
    }
}

