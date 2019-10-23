package com.starixc.adminhans;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.starixc.adminhans.Adapters.ProductAdapter;
import com.starixc.adminhans.Model.Products;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {

    private View productsView;
    private RecyclerView myProductsList;
    private ProductAdapter productAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ProductsRef = db.collection("Products");
     RecyclerView.LayoutManager layoutManager;
   // private RecyclerView.Adapter ProductAdapter;

    public ProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        productsView= inflater.inflate(R.layout.fragment_products, container, false);
       setUpRecyclerView();
        return productsView;
    }

    private void setUpRecyclerView() {
        Query query = ProductsRef.orderBy("category", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Products> options = new FirestoreRecyclerOptions.Builder<Products>()
                .setQuery(query,Products.class)
                .build();
        productAdapter = new ProductAdapter(options);
        myProductsList =(RecyclerView) productsView.findViewById(R.id.recycler_menu);
       myProductsList.setHasFixedSize(true);
        myProductsList.setLayoutManager(new GridLayoutManager(getContext(),2));
        myProductsList.setAdapter(productAdapter);


    }

    @Override
    public void onStart() {
        super.onStart();
        productAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        productAdapter.stopListening();
    }
}
