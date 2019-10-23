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


public class OrderAdapter extends FirestoreRecyclerAdapter<Order, OrderViewHolder> {
    private View.OnClickListener listener;
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
}