package com.starixc.adminhans.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.starixc.adminhans.Model.OrderProduct;
import com.starixc.adminhans.R;
import com.starixc.adminhans.viewHolder.OrderItemViewHolder;
import com.starixc.adminhans.viewHolder.OrderViewHolder;

public class OrderItemAdapter extends FirestoreRecyclerAdapter<OrderProduct, OrderItemAdapter.OrdersHolder> {
    private OnItemClickListner listner;
    private FragmentManager fm;
    private FragmentTransaction ft;


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public OrderItemAdapter(@NonNull FirestoreRecyclerOptions<OrderProduct> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrdersHolder holder, int position, @NonNull OrderProduct model) {
        holder.txtProductName.setText(model.getProductName());
        holder.txtProductQuantity.setText(model.getQuantity());
        holder.txtProductPrice.setText(model.getPrice());
    }

    @NonNull
    @Override
    public OrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_item,
                parent, false);
        return new OrdersHolder(v);
    }

    class OrdersHolder extends RecyclerView.ViewHolder {
        public TextView txtProductName, txtProductPrice,txtProductQuantity,txtProductTotal;


        public OrdersHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName=itemView.findViewById(R.id.idProductName);
            txtProductPrice=itemView.findViewById(R.id.idProductPrice);
            txtProductQuantity=itemView.findViewById(R.id.idProductQty);
            txtProductTotal=itemView.findViewById(R.id.idProductPriceTotal);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position !=RecyclerView.NO_POSITION && listner !=null)
                    {
                       listner.onItemClick(getSnapshots().getSnapshot(position),position);
                    }

                }
            });
        }
    }

    public  interface OnItemClickListner{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnClickListener(OrderItemAdapter.OnItemClickListner listener)
    {
        this.listner= listener;
    }
}
