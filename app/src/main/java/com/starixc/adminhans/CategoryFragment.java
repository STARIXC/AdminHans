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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;
import com.starixc.adminhans.Adapters.CategoryAdapter;

import com.starixc.adminhans.Adapters.MyRecyclerViewAdapter;
import com.starixc.adminhans.Adapters.ProductAdapter;
import com.starixc.adminhans.Model.Category;


import com.starixc.adminhans.Adapters.CategoryAdapter.*;
import com.starixc.adminhans.viewHolder.CategoryViewHolder;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    private static final String TAG="CategoryRecycler";
    //private List<Category> categoriesList;
    private View categoryView;
    private RecyclerView myProductsList;
//    private MyRecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    private CategoryAdapter adapter;
    private FirebaseFirestore db;
    private Query query;
    private  OnItemClickListener listener;

    RecyclerView.LayoutManager layoutManager;
   // private FirestoreRecyclerAdapter<Category, CategoryViewHolder> adapter;

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
        progressBar=(ProgressBar) categoryView.findViewById(R.id.progress_bar);
        myProductsList =(RecyclerView) categoryView.findViewById(R.id.recycler_menu_category);
        myProductsList.setLayoutManager(new GridLayoutManager(getContext(),2));
        db = FirebaseFirestore.getInstance();
        final CollectionReference categoryRef = db.collection("Category");
         query = categoryRef.orderBy("name", Query.Direction.ASCENDING);
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


    private void setUpRecyclerView() {

        FirestoreRecyclerOptions<Category> options = new FirestoreRecyclerOptions.Builder<Category>()
                .setQuery(query, Category.class)
                .build();
        adapter= new CategoryAdapter(options);

//        adapter=new FirestoreRecyclerAdapter<Category, CategoryFragment.CategoryViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull CategoryFragment.CategoryViewHolder holder, int position, @NonNull Category model) {
//                    holder.setData(model.getName(),model.getImage());
//                progressBar.setVisibility(View.GONE);
//            }
//
//            @NonNull
//            @Override
//            public CategoryFragment.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);
//
//                return new CategoryFragment.CategoryViewHolder(view);
//            }
//        };
        myProductsList.setAdapter(adapter);
       // adapter.startListening();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                showActionDialog(documentSnapshot, position);
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter !=null){
            adapter.stopListening();
        }
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
                    String name=category.getName();

                    String path = documentSnapshot.getReference().getPath();
                    //Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("category",name);
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

//    private class CategoryViewHolder extends RecyclerView.ViewHolder {
//        private View view;
//
//        CategoryViewHolder(View itemView) {
//            super(itemView);
//            view = itemView;
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
//        }
//
//        void setData(String dataTitle, String dataBody) {
//            TextView title = view.findViewById(R.id.title);
//            ImageView content = view.findViewById(R.id.thumbnail);
//            title.setText(dataTitle);
//            Picasso.get().load(dataBody).into(content);
//        }
//    }
//    public interface  OnItemClickListener{
//        void onItemClick(DocumentSnapshot documentSnapshot, int position);
//
//    }
//    public void setOnItemClickListener(OnItemClickListener listener){
//        this.listener= listener;
//
//    }

}
