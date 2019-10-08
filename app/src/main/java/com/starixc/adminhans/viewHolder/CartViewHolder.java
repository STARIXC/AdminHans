package com.starixc.adminhans.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starixc.adminhans.Interface.ItemClickListner;


public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener

{
   public TextView txtProductName, txtProductPrice,txtProductQnty,txtProductSize,txtProductTotal;

    public ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

//       // txtProductName=itemView.findViewById(R.id.idProductName);
//        txtProductPrice=itemView.findViewById(R.id.idProductPrice);
//        txtProductQnty=itemView.findViewById(R.id.idProductQty);
//        txtProductSize=itemView.findViewById(R.id.idProductWeight);
//        txtProductTotal=itemView.findViewById(R.id.idProductPriceTotal);
    }

    public void setItemClickListner(ItemClickListner itemClickListner)
    {
        this.itemClickListner = itemClickListner;
    }

    @Override
    public void onClick(View view)
    {
        itemClickListner.onClick(view, getAdapterPosition(), false);

    }


}
