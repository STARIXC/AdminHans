package com.starixc.adminhans;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.starixc.adminhans.Model.Cart;
import com.starixc.adminhans.Model.Order;
import com.starixc.adminhans.viewHolder.CartViewHolder;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewOrderItemFragment extends Fragment {
    private RecyclerView productsList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef;
    private String userID = "";
    private View ordersDetView;

    private TextView orderNo,orderDate,orderPrice,orderState,orderName,orderStreet,orderLocation,orderApartment,orderHouseNo,orderPhone,orderInstruction;

    public ViewOrderItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
               // Inflate the layout for this fragment
        ordersDetView= inflater.inflate(R.layout.fragment_view_order_item, container, false);
        orderNo=(TextView) ordersDetView.findViewById(R.id.idOrderDetNo);
        orderDate=(TextView) ordersDetView.findViewById(R.id.idOrderDetDate);
        orderPrice=(TextView) ordersDetView.findViewById(R.id.txtOrderPrice);
        orderName=(TextView) ordersDetView.findViewById(R.id.shippingName);
        orderStreet=(TextView) ordersDetView.findViewById(R.id.shippingStreet);
        orderLocation=(TextView) ordersDetView.findViewById(R.id.shippingLocation);
        orderApartment=(TextView) ordersDetView.findViewById(R.id.shippingAppartment);
        orderHouseNo=(TextView) ordersDetView.findViewById(R.id.shippingHouseNo);
        orderPhone=(TextView) ordersDetView.findViewById(R.id.shippingPhone);
        orderInstruction=(TextView) ordersDetView.findViewById(R.id.shippingInstruction);
        orderState=(TextView) ordersDetView.findViewById(R.id.txtState) ;

        Bundle bundle = getArguments();

        userID = bundle.getString("uid");
        productsList = (RecyclerView) ordersDetView.findViewById(R.id.cart_list_recycler);
        productsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        productsList.setLayoutManager(layoutManager);
        cartListRef = FirebaseDatabase.getInstance().getReference().child("CartList").child("Admin view").child(userID).child("Products");

        getOrderDetails(userID);
        getCartItems(userID);
   return ordersDetView;


    }
    @Override
    public void onStart() {
        super.onStart();

        Toast.makeText(getContext(), "Am not working " + userID, Toast.LENGTH_SHORT).show();
        getCartItems(userID);
    }


    private void getOrderDetails(String orderID) {

        DatabaseReference productRef= FirebaseDatabase.getInstance().getReference().child("Orders");
        productRef.child(orderID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    Order order =dataSnapshot.getValue(Order.class);

                    orderNo.setText("OrderNo :" +order.getOrderNo());
                    orderDate.setText("Date"+order.getDate());
                    orderPrice.setText("Price"+order.getTotalAmount());
                    orderName.setText(""+order.getName());
                    orderStreet.setText(order.getStreet());
                    orderLocation.setText(order.getDeliveryLocation());
                    orderApartment.setText(order.getApartment());
                    orderHouseNo.setText(order.getHouseNo());
                    orderPhone.setText(order.getPhone());
                    orderState.setText(order.getState());
                    orderInstruction.setText(order.getInstruction());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void getCartItems(String userID)

    {
        cartListRef = FirebaseDatabase.getInstance().getReference().child("CartList").child("Admin view").child(userID).child("Products");
        FirebaseRecyclerOptions<Cart> options=
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef,Cart.class)
                        .build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {

                holder.txtProductQnty.setText("Quantity = "+model.getQuantity());
                holder.txtProductPrice.setText("Price = "+model.getPrice()+" /=");
                holder.txtProductName.setText(model.getPname());
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_cartlist_item,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };
        productsList.setAdapter(adapter);
        adapter.startListening();

    }


    }

