package com.starixc.adminhans;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.starixc.adminhans.Adapters.OrderItemAdapter;
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
    private View ordersDetView;
    private RecyclerView ordersList;
    private OrderItemAdapter orderItemAdapter;
    //database references
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference orderRef;

    RecyclerView.LayoutManager layoutManager;
    Order currentOrder;
    OrderProduct orderProduct;
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
        Bundle bundle = getArguments();

        orderID = bundle.getString("orderNo");
        orderNo=(TextView) ordersDetView.findViewById(R.id.idOrderDetNo);
        orderDate=(TextView) ordersDetView.findViewById(R.id.idOrderDetDate);
        orderPrice=(TextView) ordersDetView.findViewById(R.id.txtOrderPrice);
        orderName=(TextView) ordersDetView.findViewById(R.id.shippingName);
        orderPhone=(TextView) ordersDetView.findViewById(R.id.shippingPhone);
        orderState=(TextView) ordersDetView.findViewById(R.id.txtState) ;

        ordersList = (RecyclerView) ordersDetView.findViewById(R.id.order_list_recycler);
        ordersList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        ordersList.setLayoutManager(layoutManager);
//        cartListRef = FirebaseDatabase.getInstance().getReference().child("CartList").child("Admin view").child(userID).child("Products");

        getOrderDetails(orderID);
        getOrderItems(orderID);
   return ordersDetView;


    }


    private void getOrderDetails(String orderID) {
        orderRef = db.collection("Orders").document(orderID);
        orderRef.get()
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
    private void getOrderItems(String orderID)

    {
//        db.collection("Orders").document(orderID).collection("orders").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        ArrayList<String> arrayList = (ArrayList<String>) document.get("orders");
//                        //Do what you need to do with your ArrayList
//                        for (String s : arrayList) {
//                            Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//            }



      FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
      CollectionReference orderlistRef = rootRef.collection("Orders");
      orderlistRef.document(orderID).collection("orders").get()
              .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<QuerySnapshot> task) {
                      if (task.isSuccessful())
                      {
                          List<Orders> orderitemList = new ArrayList<>();
                          for (QueryDocumentSnapshot document : task.getResult())
                          {
                              Orders orders = document.toObject(Orders.class);
                              orderitemList.add(orders);
                          }

                          ordersList= (RecyclerView)ordersDetView.findViewById(R.id.order_list_recycler);
                          ordersList.setHasFixedSize(true);
                          ordersList.setLayoutManager(new LinearLayoutManager(getContext()));
                          ordersList.setAdapter(orderItemAdapter);
                      }else{
                          Toast.makeText(getContext(), "Error retrieving data", Toast.LENGTH_SHORT).show();
                      }
                  }
              });


//        ordersList= (RecyclerView)ordersDetView.findViewById(R.id.order_list_recycler);
//        ordersList.setHasFixedSize(true);
//        ordersList.setLayoutManager(new LinearLayoutManager(getContext()));
//        ordersList.setAdapter(orderItemAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        //orderItemAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //orderItemAdapter.stopListening();
    }


    }

