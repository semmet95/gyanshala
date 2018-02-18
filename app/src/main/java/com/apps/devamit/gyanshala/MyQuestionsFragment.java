package com.apps.devamit.gyanshala;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyQuestionsFragment extends Fragment {
    RecyclerView mRecyclerView;

    public MyQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout= inflater.inflate(R.layout.fragment_my_feed, container, false);
        mRecyclerView=layout.findViewById(R.id.questiononlyrecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new MyQuestionsAdapter());
        return layout;
    }

    void refreshUI() {
        //Log.e("my questions :", "refreshing UI");
        //mRecyclerView.removeAllViews();
        mRecyclerView.setAdapter(new MyQuestionsAdapter());
    }
}