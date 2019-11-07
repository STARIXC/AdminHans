package com.starixc.adminhans.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(itemsList.get(position).getProductName());
        holder.productPrice.setText(itemsList.get(position).getPrice());
        holder.productQuantity.setText(itemsList.get(position).getQuantity());
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }
    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void setItemsList(List<OrderProduct> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void addItems(List<OrderProduct> itemList) {
        // itemList.clear();
        itemsList.addAll(itemList);

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName, productPrice, productQuantity, productTotalCost;


        public ViewHolder(@NonNull View view) {
            super(view);
            productName = (TextView) view.findViewById(R.id.ordersProductName);
            productPrice = (TextView) view.findViewById(R.id.ordersProductPrice);
            productQuantity = (TextView) view.findViewById(R.id.ordersProductQty);
            productTotalCost = (TextView) view.findViewById(R.id.ordersProductPriceTotal);
        }
    }
}
