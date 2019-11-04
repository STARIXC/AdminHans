package com.starixc.adminhans;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.starixc.adminhans.Adapters.Adapter;
import com.starixc.adminhans.Adapters.CustomAdapter;
import com.starixc.adminhans.Model.OrderProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment {
    protected FirebaseFirestore db;
    private String orderID = "";
    private RecyclerView recyclerView;
    private   CustomAdapter adapter;
    List<OrderProduct> itemsList;
    private OrderProduct orderProduct;
    private static ProgressDialog mProgressDialog;
    private TextView noNotesView;

    public ItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_items, container, false);
        noNotesView = view.findViewById(R.id.empty_notes_view);
        Bundle bundle = getArguments();
        orderID = bundle.getString("orderNo");

        recyclerView= view.findViewById(R.id.order_list);
        itemsList = new ArrayList<>();
        fetchJSON(orderID);


        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CustomAdapter(getActivity(), itemsList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
    private void fetchJSON(final String orderID) {

        String orderNo = orderID;
        //itemsList = new ArrayList<>();
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
                    if (jsonObject !=null){
                        JSONArray list = jsonObject.getJSONArray("listOrders");
                        if (list !=null){
                            for(int i=0; i < list.length();i++)
                            {
                                JSONObject dataobj = list.getJSONObject(i);
                                if (dataobj !=null){
                                    Log.v("data",dataobj.toString());
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
                                noNotesView.setVisibility(View.GONE);
                                //Log.v("Itemlist",itemsList.toString());

                                //Toast.makeText(getContext(), "ProductName : "+ itemsList, Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            noNotesView.setVisibility(View.VISIBLE);
                        }
                    }else {
                        Toast.makeText(getContext(), "Object Not Found", Toast.LENGTH_SHORT).show();
                        noNotesView.setVisibility(View.VISIBLE);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    //destroy progress dialog11
    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//    private void getOrderItems(String orderID) {
//        String orderNo = orderID;
//
//        db=FirebaseFirestore.getInstance();
//        DocumentReference docRef = db.collection("Order_Items").document(orderNo);
//
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Object object;
//                object = documentSnapshot.getData();
//                String json = new Gson().toJson(object);
//                Log.v("json",json);
//            }
//        });
//
//    }
    //show dialog
    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
