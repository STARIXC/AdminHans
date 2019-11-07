package com.starixc.adminhans;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.starixc.adminhans.Adapters.FeedBackAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedBackFragment extends Fragment {

    private View tView;
    private RecyclerView tList;
    private FeedBackAdapter tAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference ordersRef = db.collection("Orders").document();
    public FeedBackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed_back, container, false);
    }

}
