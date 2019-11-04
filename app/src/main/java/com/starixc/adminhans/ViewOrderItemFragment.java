package com.starixc.adminhans;


import android.content.Context;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.starixc.adminhans.Adapters.CustomAdapter;
import com.starixc.adminhans.Model.Order;
import com.starixc.adminhans.Model.OrderProduct;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewOrderItemFragment extends Fragment {
    private static final String TAG = "ViewOrderItemsFragment";
    private View ordersDetView;
    Order currentOrder;
    protected FirebaseFirestore db;
    private String orderID = "";
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    List<OrderProduct> itemsList;
    private OrderProduct orderProduct;
    private TextView orderNo, orderDate, orderPrice, orderState, orderName, orderPhone;
    public ViewOrderItemFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ordersDetView = inflater.inflate(R.layout.fragment_view_order_item, container, false);
        db = FirebaseFirestore.getInstance();
        Bundle bundle = getArguments();
        recyclerView = ordersDetView.findViewById(R.id.order_list);
        // itemsList = new ArrayList<>();
        orderID = bundle.getString("orderNo");
        orderNo = (TextView) ordersDetView.findViewById(R.id.idOrderDetNo);
        orderDate = (TextView) ordersDetView.findViewById(R.id.idOrderDetDate);
        orderPrice = (TextView) ordersDetView.findViewById(R.id.txtOrderPrice);
        orderName = (TextView) ordersDetView.findViewById(R.id.shippingName);
        orderPhone = (TextView) ordersDetView.findViewById(R.id.shippingPhone);
        orderState = (TextView) ordersDetView.findViewById(R.id.txtState);
        getOrderDetails(orderID);
        return ordersDetView;
    }

    private void setUp(String orderID) {
        loadOrderItems(orderID);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CustomAdapter(getActivity(), itemsList);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);


    }

    private void loadOrderItems(String orderID) {
        String orderNo = orderID;
        itemsList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Order_Items").document(orderNo);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Object object;
                object = documentSnapshot.getData();
                String json = new Gson().toJson(object);
                Log.v("json", json);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    if (jsonObject != null) {
                        JSONArray list = jsonObject.getJSONArray("listOrders");
                        if (list != null) {
                            itemsList.clear();
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject dataobj = list.getJSONObject(i);
                                if (dataobj != null) {
                                    Log.v("data", dataobj.toString());
                                    //  _id,discount,price,productId,productName,quantity,timeStamp
                                    orderProduct = new OrderProduct();
                                    orderProduct.setId(dataobj.getString("_id"));
                                    orderProduct.setDiscount(dataobj.getString("discount"));
                                    orderProduct.setTimeStamp(dataobj.getString("timeStamp"));
                                    orderProduct.setProductName(dataobj.getString("productName"));
                                    orderProduct.setQuantity(dataobj.getString("quantity"));
                                    orderProduct.setQuantity(dataobj.getString("price"));
                                    itemsList.add(orderProduct);
                               }
                            }
                            // itemsList.clear();
                            adapter.addItems(itemsList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
                        if (documentSnapshot.exists()) {
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
        setUp(orderID);
    }
}

