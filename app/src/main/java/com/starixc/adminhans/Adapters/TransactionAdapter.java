package com.starixc.adminhans.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.starixc.adminhans.Model.Transaction;
import com.starixc.adminhans.R;

public class TransactionAdapter extends FirestoreRecyclerAdapter<Transaction, TransactionAdapter.TransactionViewHolder> {
    
 
    public TransactionAdapter(@NonNull FirestoreRecyclerOptions<Transaction> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TransactionAdapter.TransactionViewHolder holder, int position, @NonNull Transaction model) {
        String state = model.getState();
        if (state.equals("0")) {
            holder.txtOrderState.setText(R.string.order_placed);
        } else if (state.equals("1")) {
            holder.txtOrderState.setText(R.string.on_transit);
        } else if (state.equals("2")) {
            holder.txtOrderState.setText(R.string.order_shipped);
        } else {
            holder.txtOrderState.setText(R.string.order_placed);
        }
        holder.txtOrderNo.setText("OrderNo :"+model.getOrderNo());
        holder.txtOrderDate.setText(model.getOrderTime());
        holder.txtPrice.setText("Total Amount :" +model.getTotal()+ " /=");
        holder.txtOrderName.setText("Ordered By :"+model.getPhone());
    }

    @NonNull
    @Override
    public TransactionAdapter.TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complete_orders_layout,parent,false);
        TransactionViewHolder holder = new TransactionViewHolder(view);
        return holder;
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        public TextView txtOrderNo,txtPrice,txtOrderName,txtOrderDate,txtOrderState;
        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderNo =(TextView) itemView.findViewById(R.id.completeOrderNo);
            txtPrice =(TextView) itemView.findViewById(R.id.completeOrderPriceTotal);
            txtOrderName =(TextView) itemView.findViewById(R.id.completeClient);
            txtOrderDate =(TextView) itemView.findViewById(R.id.completeOrderDate);
            txtOrderState=(TextView)itemView.findViewById(R.id.completeOrderState);

        }
    }
}
