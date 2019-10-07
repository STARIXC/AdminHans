package com.starixc.adminhans;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewOrderItemFragment extends Fragment {
    private RecyclerView productList;
    private View ordersDetView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef;

    private TextView orderNo,orderDate,orderPrice,orderState,orderName,orderStreet,orderLocation,orderApartment,orderHouseNo,orderPhone,orderInstruction;
    private String orderID="",saveCurrentDate,saveCurrentTime,state;



    public ViewOrderItemFragment() {
        // Required empty public constructor
    }
//bundle.putString("uid",order.getPhone());
//                        bundle.putString("orderNo",order.getOrderNo());
//                        bundle.putString("apartment",order.getApartment());
//                        bundle.putString("date",order.getDate());
//                        bundle.putString("name",order.getName());
//                        bundle.putString("phone",order.getPhone());
//                        bundle.putString("state",order.getState());
//                        bundle.putString("street",order.getStreet());
//                        bundle.putString("houseNo",order.getHouseNo());
//                        bundle.putString("totalAmount",order.getTotalAmount());
//                        bundle.putString("deliveryLocation",order.getDeliveryLocation());
//                        bundle.putString("Instruction",order.getInstruction());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
               // Inflate the layout for this fragment
        ordersDetView= inflater.inflate(R.layout.fragment_view_order_item, container, false);
        Bundle bundle = getArguments();

        orderID = bundle.getString("uid");
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
       // orderNo=(TextView) ordersDetView.findViewById(R.id.idOrderDetNo);



        cartListRef = FirebaseDatabase.getInstance().getReference().child("CartList").child("Admin View");




   return ordersDetView;


    }

}
