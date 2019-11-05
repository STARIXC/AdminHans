package com.starixc.adminhans;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;
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
    private CollectionReference requests ;
    private String orderID = "";
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    List<OrderProduct> itemsList;
    private Order item;
    private OrderProduct orderProduct;
    private TextView orderNo, orderDate, orderPrice, orderState, orderName, orderPhone,orderStateBtn ;
    MaterialSpinner spinner;
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
        orderStateBtn=(TextView) ordersDetView.findViewById(R.id.completeOrder);
        getOrderDetails(orderID);
        orderStateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showActionDialog();
            }
        });
        return ordersDetView;
    }
    private void showActionDialog() {
        CharSequence colors[]= new CharSequence[]
                {
                        "Update","Delete"
                };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose Option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    showAlertDialog();
                } else{
                    deleteOrder(orderID);
                }
            }
        });
        builder.show();
    }

    private void deleteOrder(final String orderID) {
        db.collection("Orders").document(orderID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        Toast.makeText(getContext(), "Order successfully deleted!", Toast.LENGTH_SHORT).show();
                        db.collection("Order_Items").document(orderID)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                        //  Toast.makeText(getContext(), "Order successfully deleted!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error deleting document", e);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });

    }

    private void showAlertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Update Order");
        alert.setMessage("Please change status");

        final View CustomDialog = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        spinner=(MaterialSpinner)CustomDialog.findViewById(R.id.statusSpinner);
        spinner.setItems("Placed","On my way","Shipped");
        final String orderNo =orderID;
        alert.setView(CustomDialog);
        alert.setCancelable(false);
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
            //item.setState(String.valueOf(spinner.getSelectedIndex()));
            int option=spinner.getSelectedIndex();
            String selected=String.valueOf(option);
           requests=db.collection("Orders");
           requests.document(orderNo).update("state",selected);
           getOrderDetails(orderNo);
 }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();

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
                            String ordersState=currentOrder.getState();
                            if (ordersState.equals("0")){
                                orderState.setText(R.string.order_placed);
                            }

                            else if (currentOrder.getState().equals("1")) {
                                orderState.setText(R.string.on_transit);
                            }
                           else if (currentOrder.getState().equals("2")) {
                                orderState.setText(R.string.order_shipped);
                            }else {
                                orderState.setText(R.string.order_placed);
                            }

                        }
                    }
                });
        setUp(orderID);
    }
}

