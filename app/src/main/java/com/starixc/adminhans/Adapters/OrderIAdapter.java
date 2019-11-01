package com.starixc.adminhans.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starixc.adminhans.Model.OrderProduct;
import com.starixc.adminhans.OrderedItemsFragment;
import com.starixc.adminhans.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OrderIAdapter extends RecyclerView.Adapter<OrderIAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<OrderProduct> itemsList;

    public OrderIAdapter(OrderedItemsFragment ctx, ArrayList<OrderProduct> itemsList) {
        inflater=LayoutInflater.from(ctx.getContext());
        this.itemsList = itemsList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtProductName, txtProductPrice, txtProductQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.idProductName);
            txtProductPrice = itemView.findViewById(R.id.idProductPrice);
            txtProductQuantity = itemView.findViewById(R.id.idProductQty);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_order_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtProductName.setText(itemsList.get(position).getProductName());
        holder.txtProductQuantity.setText(itemsList.get(position).getQuantity());
        //holder.txtProductPrice.setText(itemList.get(position).getPrice());
        Locale locale = new Locale("en","KE");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price =(Integer.parseInt(itemsList.get(position).getPrice()))*(Integer.parseInt(itemsList.get(position).getQuantity()));
        holder.txtProductPrice.setText(fmt.format(price));
    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
