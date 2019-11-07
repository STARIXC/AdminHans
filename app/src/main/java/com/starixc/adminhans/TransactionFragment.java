package com.starixc.adminhans;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.starixc.adminhans.Adapters.TransactionAdapter;
import com.starixc.adminhans.Model.Order;
import com.starixc.adminhans.Model.Transaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment {
    private View tView;
    private RecyclerView tList;
    private TransactionAdapter tAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ordersRef = db.collection("Orders");


    public TransactionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tView= inflater.inflate(R.layout.fragment_transaction, container, false);
        loadFulfilledOrders();
        return tView;
    }

    private void loadFulfilledOrders() {
        Query query=ordersRef.whereEqualTo("state","2");
        FirestoreRecyclerOptions<Transaction> options = new FirestoreRecyclerOptions.Builder<Transaction>()
                .setQuery(query,Transaction.class)
                .build();
        tAdapter= new TransactionAdapter(options);
        tList=(RecyclerView) tView.findViewById(R.id.transaction_list_recycler);
        tList.setHasFixedSize(true);
        tList.setLayoutManager(new LinearLayoutManager(getContext()));
        tList.setAdapter(tAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        tAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        tAdapter.stopListening();
    }
}
