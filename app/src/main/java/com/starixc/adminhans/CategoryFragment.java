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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.starixc.adminhans.Adapters.CategoryAdapter;

import com.starixc.adminhans.Adapters.ProductAdapter;
import com.starixc.adminhans.Model.Category;


import com.starixc.adminhans.Adapters.CategoryAdapter.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    private View categoryView;
    private RecyclerView myProductsList;
    private CategoryAdapter categoryAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference categoryRef = db.collection("Category");
    RecyclerView.LayoutManager layoutManager;


    private FragmentManager fm;
    private FragmentTransaction ft;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        categoryView = inflater.inflate(R.layout.fragment_category, container, false);
        FloatingActionButton fab = (FloatingActionButton) categoryView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fm = getActivity().getSupportFragmentManager();
                ft = fm.beginTransaction();
                AddCategoryFragment addCategoryFragment = new AddCategoryFragment();
                ft.replace(R.id.fragment, addCategoryFragment).addToBackStack(null);
                ft.commit();
            }
        });

        setUpRecyclerView();



        return categoryView;
    }
    @Override
    public void onStart() {
        super.onStart();
        categoryAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        categoryAdapter.stopListening();
    }


    private void setUpRecyclerView() {
        Query query = categoryRef;
        FirestoreRecyclerOptions<Category> options = new FirestoreRecyclerOptions.Builder<Category>()
                .setQuery(query, Category.class)
                .build();
        categoryAdapter = new CategoryAdapter(options);
        myProductsList =(RecyclerView) categoryView.findViewById(R.id.recycler_menu_category);
        myProductsList.setHasFixedSize(true);
        myProductsList.setLayoutManager(new GridLayoutManager(getContext(),2));
        myProductsList.setAdapter(categoryAdapter);
        categoryAdapter.setOnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                showActionDialog(documentSnapshot, position);
            }
        });

    }

    private void showActionDialog(final DocumentSnapshot documentSnapshot, final int position) {
        CharSequence colors[] = new CharSequence[]
                {
                        "Add New Product", "Delete"
                };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose Option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    Category category = documentSnapshot.toObject(Category.class);
                    String id = documentSnapshot.getId();

                    String path = documentSnapshot.getReference().getPath();
                    //Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("cid", id);
                    fm = getActivity().getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    AddProductFragment addProductFragment = new AddProductFragment();
                    addProductFragment.setArguments(bundle);
                    ft.replace(R.id.fragment, addProductFragment).addToBackStack(null);
                    ft.commit();
                } else {
                    deleteOrder(documentSnapshot, position);
                }
            }
        });
        builder.show();
    }

    private void deleteOrder(DocumentSnapshot documentSnapshot, int position) {
        Category category = documentSnapshot.toObject(Category.class);
        String id = documentSnapshot.getId();
        String path = documentSnapshot.getReference().getPath();
        //Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
        String categoryID = id;
        db.collection("Category").document(categoryID)
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


}
