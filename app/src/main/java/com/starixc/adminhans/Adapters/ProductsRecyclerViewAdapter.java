package com.starixc.adminhans.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.starixc.adminhans.Model.OrderProduct;
import com.starixc.adminhans.R;

import java.util.ArrayList;
import java.util.List;

public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder> {

    private  List<DocumentReference> productList;
    private Context context;
    private FirebaseFirestore db;
    public ProductsRecyclerViewAdapter(List<DocumentReference> productList, Context context, FirebaseFirestore db) {
        this.productList = productList;
        this.context = context;
        this.db = db;
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }
    @NonNull
    @Override
    public ProductsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_order_item,parent,false);
        ProductsRecyclerViewAdapter.ViewHolder viewHolder =
                new ProductsRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int itemPos = position;
        final DocumentReference orderProduct=productList.get(position);
//        holder.txtProductName.setText(orderProduct.get().get);
//        holder.txtProductQuantity.setText(orderProduct.getQuantity());
//        holder.txtProductPrice.setText(orderProduct.getPrice());
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtProductName, txtProductPrice,txtProductQuantity,txtProductTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName=itemView.findViewById(R.id.idProductName);
            txtProductPrice=itemView.findViewById(R.id.idProductPrice);
            txtProductQuantity=itemView.findViewById(R.id.idProductQty);
            txtProductTotal=itemView.findViewById(R.id.idProductPriceTotal);
        }
    }

}
