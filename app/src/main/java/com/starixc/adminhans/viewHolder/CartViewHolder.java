package com.starixc.adminhans.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starixc.adminhans.Interface.ItemClickListner;
import com.starixc.adminhans.R;

import java.text.BreakIterator;


public class CartViewHolder extends RecyclerView.ViewHolder

{
   public TextView txtProductName, txtProductPrice,txtProductQnty,txtProductSize,txtProductTotal;



    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

       txtProductName=itemView.findViewById(R.id.idProductName);
        txtProductPrice=itemView.findViewById(R.id.idProductPrice);
        txtProductQnty=itemView.findViewById(R.id.idProductQty);
       // txtProductSize=itemView.findViewById(R.id.idProductWeight);
        txtProductTotal=itemView.findViewById(R.id.idProductPriceTotal);
    }




}
