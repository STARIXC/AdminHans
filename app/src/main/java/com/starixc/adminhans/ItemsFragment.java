package com.starixc.adminhans;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.starixc.adminhans.Model.OrderProduct;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment {
    protected FirebaseFirestore db;
    private final int jsoncode=1;
    private String orderID = "";
    private RecyclerView recyclerView;
    ArrayList<OrderProduct> itemsList;
    private OrderProduct orderProduct;
    private static ProgressDialog mProgressDialog;


    public ItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_items, container, false);
        recyclerView= view.findViewById(R.id.order_list_recycler);
        Bundle bundle = getArguments();
        orderID = bundle.getString("orderNo");

        fetchJSON(orderID);
        return view;
    }

    private void fetchJSON(final String orderID) {
        showSimpleProgressDialog(getContext(), "Loading...","Fetching OrderItems",false);
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                String response="";

                itemsList = new ArrayList<>();
                try {
                    getOrderItems(orderID);
                }catch (Exception e)
                {
                    response=e.getMessage();
                }
                return null;
            }
        };

    }
    //destroy progress dialog
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
    private void getOrderItems(String orderID) {
        String orderNo = orderID;

        db=FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Order_Items").document(orderNo);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Object object;
                object = documentSnapshot.getData();
                String json = new Gson().toJson(object);
                Log.v("json",json);
            }
        });

    }
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
