package com.starixc.adminhans.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.starixc.adminhans.Model.OrderProduct;
import com.starixc.adminhans.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private ArrayList<OrderProduct> itemList=new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView productName,productPrice,productQuantity,productTotalCost;

        public MyViewHolder(View view) {
            super(view);
            productName = (TextView) view.findViewById(R.id.orderProductName);
            productPrice = (TextView) view.findViewById(R.id.orderProductPrice);
            productQuantity = (TextView) view.findViewById(R.id.orderProductQty);
            productTotalCost = (TextView) view.findViewById(R.id.orderProductPriceTotal);

        }
    }


    public Adapter(ArrayList<OrderProduct> itemList) {
        this.itemList = itemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OrderProduct orderProduct = itemList.get(position);
        holder.productName.setText(itemList.get(position).getProductName());
        holder.productPrice.setText(itemList.get(position).getPrice());
        holder.productQuantity.setText(itemList.get(position).getQuantity());
//        holder.productTotalCost.setText(itemList.get(position).getP());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

