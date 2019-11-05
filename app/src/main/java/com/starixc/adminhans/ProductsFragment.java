package com.starixc.adminhans;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.starixc.adminhans.Adapters.ProductAdapter;
import com.starixc.adminhans.Model.Products;
import com.starixc.adminhans.R;
import static com.starixc.adminhans.Adapters.ProductAdapter.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {
    private OnItemClickListener listener;

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
        productAdapter.setOnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                showActionDialog( documentSnapshot,position);
            }
        });


    }
    private void showActionDialog(final DocumentSnapshot documentSnapshot, final int position) {
        CharSequence colors[]= new CharSequence[]
                {
                        "Update","Delete"
                };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose Option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    Products products = documentSnapshot.toObject(Products.class);
                    String id =documentSnapshot.getId();
                    String path = documentSnapshot.getReference().getPath();
                    //Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("pid",id);
                    FragmentManager fm=getActivity().getSupportFragmentManager();
                    FragmentTransaction ft =fm.beginTransaction();
                    ProductManageFragment productManageFragment= new ProductManageFragment();
                    productManageFragment.setArguments(bundle);
                    ft.replace(R.id.fragment,productManageFragment) .addToBackStack(null);
                    ft.commit();
                } else{
                    deleteOrder(documentSnapshot,position);
                }
            }
        });
        builder.show();
    }

    private void deleteOrder(DocumentSnapshot documentSnapshot, int position) {
        Products products = documentSnapshot.toObject(Products.class);
        String id =documentSnapshot.getId();
        String path = documentSnapshot.getReference().getPath();
        //Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
        String productID = id;
        db.collection("Products").document(productID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Deleting...:", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ERROR DELETING", "Error deleting document", e);
                                          }
                });
    }

//    private void showAlertDialog() {
//    }

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
