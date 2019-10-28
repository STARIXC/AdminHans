package com.starixc.adminhans.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starixc.adminhans.Interface.ItemClickListner;
import com.starixc.adminhans.R;

public class OrderItemViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
    public TextView txtProductName, txtProductPrice,txtProductQuantity,txtProductTotal;
    private ItemClickListner itemClickListner;

    public OrderItemViewHolder(@NonNull View itemView) {
        super(itemView);
        txtProductName=itemView.findViewById(R.id.idProductName);
        txtProductPrice=itemView.findViewById(R.id.idProductPrice);
        txtProductQuantity=itemView.findViewById(R.id.idProductQty);
        txtProductTotal=itemView.findViewById(R.id.idProductPriceTotal);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    public void setTxtProductName(TextView txtProductName) {
        this.txtProductName = txtProductName;
    }

    @Override
    public void onClick(View view) {

    }
}
