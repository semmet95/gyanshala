package com.apps.devamit.gyanshala;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyAnswersFragment extends Fragment {

    public MyAnswersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout= inflater.inflate(R.layout.fragment_my_answers, container, false);
        //use layout.findViewById, it doesn't require type casting
        return layout;
    }

}
