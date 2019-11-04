package com.starixc.adminhans.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starixc.adminhans.ItemsFragment;
import com.starixc.adminhans.Model.OrderProduct;
import com.starixc.adminhans.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private Context context;
    private List<OrderProduct> itemsList;

    public CustomAdapter(Context context, List<OrderProduct> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(itemsList.get(position).getProductName());
        holder.productPrice.setText(itemsList.get(position).getPrice());
        holder.productQuantity.setText(itemsList.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName, productPrice, productQuantity, productTotalCost;

        public ViewHolder(@NonNull View view) {
            super(view);
            productName = (TextView) view.findViewById(R.id.orderProductName);
            productPrice = (TextView) view.findViewById(R.id.orderProductPrice);
            productQuantity = (TextView) view.findViewById(R.id.orderProductQty);
            productTotalCost = (TextView) view.findViewById(R.id.orderProductPriceTotal);
        }
    }
}
