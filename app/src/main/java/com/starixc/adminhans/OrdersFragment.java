package com.starixc.adminhans;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.starixc.adminhans.Adapters.OrderAdapter;
import com.starixc.adminhans.Adapters.OrderAdapter.*;

import com.starixc.adminhans.Model.Order;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {
    private OnItemClickListener listener;
    private View ordersView;
    private RecyclerView ordersList;
    private OrderAdapter orderAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ordersRef = db.collection("Orders");
    RecyclerView.LayoutManager layoutManager;

    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ordersView= inflater.inflate(R.layout.fragment_orders, container, false);
        loadOrderList();
        return  ordersView;
    }
    private void loadOrderList() {
        Query query = ordersRef.orderBy("orderNo", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Order> options = new FirestoreRecyclerOptions.Builder<Order>()
                .setQuery(query,Order.class)
                .build();
        orderAdapter = new OrderAdapter(options);
        ordersList =(RecyclerView) ordersView.findViewById(R.id.order_list_recycler);
        ordersList.setHasFixedSize(true);
        ordersList.setLayoutManager(new LinearLayoutManager(getContext()));
        ordersList.setAdapter(orderAdapter);
        orderAdapter.setOnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Order orders = documentSnapshot.toObject(Order.class);
                String id= documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                Bundle bundle = new Bundle();
                bundle.putString("orderNo",id);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ViewOrderItemFragment itemFragment = new ViewOrderItemFragment();
                itemFragment.setArguments(bundle);
                ft.replace(R.id.fragment,itemFragment).addToBackStack(null);
                ft.commit();
            }
        });




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

