package com.apps.devamit.gyanshala;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyAnswersFragment extends Fragment {
    RecyclerView mRecyclerView;

    public MyAnswersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout= inflater.inflate(R.layout.fragment_my_feed, container, false);
        mRecyclerView=layout.findViewById(R.id.questiononlyrecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new MyAnswersAdapter());
        return layout;
    }

    void refreshUI() {
        mRecyclerView.setAdapter(new MyAnswersAdapter());
    }
}
