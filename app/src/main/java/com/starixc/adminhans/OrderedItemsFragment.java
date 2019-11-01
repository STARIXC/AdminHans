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

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.starixc.adminhans.Adapters.OrderIAdapter;
import com.starixc.adminhans.Adapters.OrderItemAdapter;
import com.starixc.adminhans.Adapters.OrdersAdapter;
import com.starixc.adminhans.Adapters.ProductsRecyclerViewAdapter;
import com.starixc.adminhans.Model.Order;
import com.starixc.adminhans.Model.OrderProduct;
import com.starixc.adminhans.viewHolder.OrderItemViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderedItemsFragment extends Fragment {

    private static final String TAG = "ViewOrderItemsFragment";
    private View ordersView;
    private RecyclerView ordersList;
    FirebaseFirestore db;
    OrderProduct orderProduct;
      private String orderID = "";
    ArrayList<OrderProduct> itemsList;
    private OrderIAdapter adapter;
    public OrderedItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ordersView = inflater.inflate(R.layout.fragment_ordered_items, container, false);

        Bundle bundle = getArguments();
        orderID = bundle.getString("orderNo");

        TextView orderNoTxt = ordersView.findViewById(R.id.txt_OrderNo);
        orderNoTxt.setText(orderID);
        ordersList = ordersView.findViewById(R.id.order_list_recycler);

        ordersList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        ordersList.setLayoutManager(layoutManager);
        getOrderItems(orderID);

        return ordersView;
    }

    private void getOrderItems(String orderID) {
        String orderNo = orderID;
        itemsList = new ArrayList<>();
        db=FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Order_Items").document(orderNo);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Object object;
                object = documentSnapshot.getData();
                String json = new Gson().toJson(object);
                Log.v("json",json);

                try {
                  JSONObject jsonObject = new JSONObject(json);
                  JSONArray dataArray = jsonObject.getJSONArray("listOrders");
                  for(int i=0; i < dataArray.length();i++)
                  {
                      orderProduct = new OrderProduct();
                      JSONObject dataobj = dataArray.getJSONObject(i);
                      orderProduct.setProductName(dataobj.getString("productName"));
                      orderProduct.setQuantity(dataobj.getString("quantity"));
                      orderProduct.setQuantity(dataobj.getString("price"));
                      itemsList.add(orderProduct);
                      Toast.makeText(getContext(), "ProductName : "+ itemsList, Toast.LENGTH_SHORT).show();

                  }

//                    JSONObject infoObj = new JSONObject("listOrders");
//                    Iterator<String> iterator =infoObj.keys();
//                 while (iterator.hasNext()){
//                     String key = iterator.next();
//                     JSONObject objArray= infoObj.getJSONObject(key);
//                     orderProduct = new OrderProduct();
//                     orderProduct.setProductName(objArray.getString("productName"));
//                     orderProduct.setQuantity(objArray.getString("quantity"));
//                     orderProduct.setQuantity(objArray.getString("price"));
//                     itemsList.add(orderProduct);
//
//
//                 }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        adapter = new OrderIAdapter(this,itemsList);
        ordersList.setAdapter(adapter);
    }


}
