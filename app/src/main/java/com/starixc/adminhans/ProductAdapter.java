package com.starixc.adminhans;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;
import com.starixc.adminhans.Model.Products;
import com.starixc.adminhans.viewHolder.ProductViewHolder;


public class ProductAdapter extends FirestoreRecyclerAdapter<Products, ProductViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ProductAdapter(@NonNull FirestoreRecyclerOptions<Products> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
        holder.txtProductName.setText(model.getName());
        // holder.txtProductDescription.setText(model.getDescription());
        holder.txtProductPrice.setText("Ksh. " + model.getPrice() + "/=");
        Picasso.get().load(model.getImage()).into(holder.imageView);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }
}