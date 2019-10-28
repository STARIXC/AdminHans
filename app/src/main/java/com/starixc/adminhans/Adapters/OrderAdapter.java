package com.starixc.adminhans.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;
import com.starixc.adminhans.Model.Order;
import com.starixc.adminhans.R;
import com.starixc.adminhans.viewHolder.OrderViewHolder;


public class OrderAdapter extends FirestoreRecyclerAdapter<Order, OrderAdapter.OrderViewHolder> {
    private OnItemClickListener listener;
    private FragmentManager fm;
    private FragmentTransaction ft;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public OrderAdapter(@NonNull FirestoreRecyclerOptions<Order> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int position, @NonNull Order model) {
        orderViewHolder.txtOrderNo.setText("OrderNo :"+model.getOrderNo());
        orderViewHolder.txtOrderDate.setText(model.getOrderTime());
        orderViewHolder.txtPrice.setText("Total Amount :" +model.getTotal()+ " /=");
    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
        OrderViewHolder holder = new OrderViewHolder(view);
        return holder;

    }
    class OrderViewHolder extends RecyclerView.ViewHolder{
        public TextView txtOrderNo, txtPrice,txtOrderDate;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderDate =(TextView) itemView.findViewById(R.id.idOrderDate);
            txtOrderNo= (TextView)itemView.findViewById(R.id.idOrderNo);
            txtPrice = (TextView) itemView.findViewById(R.id.idOrderPriceTotal);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position !=RecyclerView.NO_POSITION && listener!=null)
                    {
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }

    public interface  OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }
    public void setOnClickListener(OnItemClickListener listener){
        this.listener= listener;

    }
}