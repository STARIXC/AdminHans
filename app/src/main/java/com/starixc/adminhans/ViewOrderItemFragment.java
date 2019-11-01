package com.starixc.adminhans;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.starixc.adminhans.Adapters.OrderItemAdapter;
import com.starixc.adminhans.Adapters.ProductsRecyclerViewAdapter;
import com.starixc.adminhans.Model.Cart;
import com.starixc.adminhans.Model.Order;
import com.starixc.adminhans.Model.OrderProduct;
import com.starixc.adminhans.Model.Orders;
import com.starixc.adminhans.viewHolder.CartViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewOrderItemFragment extends Fragment {

    private static final String TAG ="ViewOrderItemsFragment";
    private View ordersDetView;
    private RecyclerView ordersList;
    //database references
    private FirebaseFirestore db;
//    private OrderItemAdapter orderItemAdapter;

//    private DocumentReference orderRef;
//
//    RecyclerView.LayoutManager layoutManager;
    Order currentOrder;
//    OrderProduct orderProduct;
    private String orderID = "";
    private TextView orderNo,orderDate,orderPrice,orderState,orderName,orderPhone;

    public ViewOrderItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
               // Inflate the layout for this fragment
        ordersDetView= inflater.inflate(R.layout.fragment_view_order_item, container, false);
        db=FirebaseFirestore.getInstance();
        Bundle bundle = getArguments();

        orderID = bundle.getString("orderNo");
        orderNo=(TextView) ordersDetView.findViewById(R.id.idOrderDetNo);
        orderDate=(TextView) ordersDetView.findViewById(R.id.idOrderDetDate);
        orderPrice=(TextView) ordersDetView.findViewById(R.id.txtOrderPrice);
        orderName=(TextView) ordersDetView.findViewById(R.id.shippingName);
        orderPhone=(TextView) ordersDetView.findViewById(R.id.shippingPhone);
        orderState=(TextView) ordersDetView.findViewById(R.id.txtState) ;
        Button viewOrderBtn=(Button) ordersDetView.findViewById(R.id.viewOrderItemsBtn);
        viewOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOrderItems(orderID);
            }
        });
        getOrderDetails(orderID);

        return ordersDetView;


    }

    private void loadOrderItems(String orderID) {
        String id=orderID;
        Bundle bundle = new Bundle();
        bundle.putString("orderNo",id);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        OrderedItemsFragment orderedItemsFragment = new OrderedItemsFragment();
        orderedItemsFragment.setArguments(bundle);
        ft.replace(R.id.fragment,orderedItemsFragment).addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void getOrderDetails(String orderID) {

      db.collection("Orders").document(orderID)
        .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists())
                        {
                          currentOrder = documentSnapshot.toObject(Order.class);
                            orderNo.setText(currentOrder.getOrderNo());
                            orderDate.setText(currentOrder.getOrderTime());
                            orderPrice.setText(currentOrder.getTotal());
                            orderName.setText(currentOrder.getName());
                            orderPhone.setText(currentOrder.getPhone());
                            orderState.setText(currentOrder.getState());
                        }
                    }
                });


    }


    }

