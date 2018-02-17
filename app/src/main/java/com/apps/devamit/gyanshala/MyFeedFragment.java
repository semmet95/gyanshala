package com.apps.devamit.gyanshala;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyFeedFragment extends Fragment {
    RecyclerView mRecyclerView;

    public MyFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout= inflater.inflate(R.layout.fragment_my_feed, container, false);
        Log.e("myfeedfragment :", "here in");
        mRecyclerView=layout.findViewById(R.id.questiononlyrecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new MyFeedAdapter());
        return layout;
    }

    void refreshUI() {
        Log.e("myfeedfragment :", "here in refresh UI");
        mRecyclerView.setAdapter(new MyFeedAdapter());
    }
}
