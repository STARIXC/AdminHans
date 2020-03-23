package com.starixc.adminhans.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.starixc.adminhans.Model.Order;
import com.starixc.adminhans.Model.OrderStatus;
import com.starixc.adminhans.R;

import java.util.Random;

import static android.os.Build.VERSION_CODES.P;

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
        String channelId= getString(R.string.default_notification_channel_id);
        Intent intent = new Intent(this, OrderStatus.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Hans Liquor Order")
                .setContentInfo("New Order")
                .setContentText("You have new order # "+id)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        int randomInt = new Random().nextInt(999-1)+1;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelId,"Firebase_Main",NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(randomInt,notificationBuilder.build());

    }

    public ListenOrder() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
