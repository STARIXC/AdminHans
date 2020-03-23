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

import java.util.Collections;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<OrderProduct> itemsList = Collections.emptyList();
    OrderProduct current;
    int currentPos = 0;

    public CustomAdapter(Context context, List<OrderProduct> itemsList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder myHolder = (ViewHolder) holder;
        OrderProduct current = itemsList.get(position);
        myHolder.productName.setText(current.getProductName());
        myHolder.productPrice.setText(" Ksh. :" + current.getPrice() + "/=");
        myHolder.productQuantity.setText("QTY: " + current.getQuantity());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
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
