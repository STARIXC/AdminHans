package com.starixc.adminhans.viewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starixc.adminhans.R;

public class OrderViewHolder extends RecyclerView.ViewHolder  {


    public TextView txtOrderNo, txtPrice,txtOrderDate;
    public Button orderDetBtn;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

 txtOrderDate =(TextView) itemView.findViewById(R.id.idOrderDate);
 txtOrderNo= (TextView)itemView.findViewById(R.id.idOrderNo);
 txtPrice = (TextView) itemView.findViewById(R.id.idOrderPriceTotal);
 orderDetBtn=(Button) itemView.findViewById(R.id.orderDetailsBtn);

    }


}
