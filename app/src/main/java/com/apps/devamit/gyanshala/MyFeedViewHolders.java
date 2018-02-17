package com.apps.devamit.gyanshala;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import at.blogc.android.views.ExpandableTextView;

public class MyFeedViewHolders extends RecyclerView.ViewHolder {
    TextView questioncardtitle, questioncarddetails, answernums;

    public MyFeedViewHolders(View itemView) {
        super(itemView);
        questioncardtitle=itemView.findViewById(R.id.questioncardtitle);
        questioncarddetails=itemView.findViewById(R.id.questionDetails);
        answernums=itemView.findViewById(R.id.answernums);
    }
}