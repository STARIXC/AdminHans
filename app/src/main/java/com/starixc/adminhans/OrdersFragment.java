package com.starixc.adminhans;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.starixc.adminhans.Model.Order;
import com.starixc.adminhans.viewHolder.OrderViewHolder;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {
    private View ordersView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private RecyclerView ordersList;
    private RecyclerView.LayoutManager layoutManager;
    private TextView txtTotalAmount,tvmsg;
   private DatabaseReference ordersRef;

    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ordersView= inflater.inflate(R.layout.fragment_orders, container, false);

        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        ordersList =ordersView.findViewById(R.id.order_list_recycler);
        ordersList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        ordersList.setLayoutManager(layoutManager);



        return  ordersView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Order> options=
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(ordersRef,Order.class)
                        .build();
        FirebaseRecyclerAdapter<Order, OrderViewHolder> adapter = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, final int i, @NonNull final Order order) {
                orderViewHolder.txtOrderNo.setText("OrderNo :"+order.getOrderNo());
                orderViewHolder.txtOrderDate.setText(order.getDate()+ " Time :" +order.getTime());
                orderViewHolder.txtPrice.setText("Total Amount" +order.getTotalAmount());
                orderViewHolder.orderDetBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                         String uID= getRef(i).getKey();
                        Bundle bundle = new Bundle();
                        bundle.putString("uid",uID);
                        bundle.putString("orderNo",order.getOrderNo());
                        bundle.putString("apartment",order.getApartment());
                        bundle.putString("date",order.getDate());
                        bundle.putString("name",order.getName());
                        bundle.putString("phone",order.getPhone());
                        bundle.putString("state",order.getState());
                        bundle.putString("street",order.getStreet());
                        bundle.putString("houseNo",order.getHouseNo());
                        bundle.putString("totalAmount",order.getTotalAmount());
                        bundle.putString("deliveryLocation",order.getDeliveryLocation());
                        bundle.putString("Instruction",order.getInstruction());
                        fm=getActivity().getSupportFragmentManager();
                        ft =fm.beginTransaction();
                        ViewOrderItemFragment viewOrderItemFragment= new ViewOrderItemFragment();
                        viewOrderItemFragment.setArguments(bundle);
                        ft.replace(R.id.fragment,viewOrderItemFragment) .addToBackStack(null);
                        ft.commit();
                    }
                });
                orderViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        CharSequence options[] =new CharSequence[]{
                                "Yes",
                                "No"

                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Have you shipped this order products?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i==0){
                                    String uID = getRef(i).getKey();
                                    RemoverOrder(uID);

                                }
                                else {
                                    //finish();
                                }

                            }
                        });
                        builder.show();
                    }
                });

            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.orders_layout, parent,false);
                OrderViewHolder holder = new OrderViewHolder(view);
                return holder;
            }
        };
        ordersList.setAdapter(adapter);
        adapter.startListening();

    }
    private void RemoverOrder(String uID) {
        ordersRef.child(uID).removeValue();
    }
}

