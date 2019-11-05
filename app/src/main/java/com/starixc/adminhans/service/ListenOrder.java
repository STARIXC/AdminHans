package com.starixc.adminhans.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.starixc.adminhans.Model.Order;

public class ListenOrder extends Service  {
    private FirebaseFirestore db;
    private CollectionReference requests;

    @Override
    public void onCreate() {
        super.onCreate();
        db = FirebaseFirestore.getInstance();
        requests = db.collection("Orders");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
     requests.addSnapshotListener( new EventListener<QuerySnapshot>() {
         @Override
         public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
             if (e !=null){
                 Log.w("Collection Changes","listen:error",e);
                 return;
             }
             for (DocumentChange dc: documentSnapshots.getDocumentChanges() ){
                   Order order = dc.getDocument().toObject(Order.class);
                 if (order.getState().equals("0")){
                     showNotification(order);
                 }
             }
         }
     });
        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotification(Order order) {
        String id= order.getOrderNo();

    }

    public ListenOrder() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
