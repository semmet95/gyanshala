package com.apps.devamit.gyanshala;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyQuestionsAdapter extends RecyclerView.Adapter<MyQuestionsAdapter.MyQuestionsViewHolders> {

    @Override
    public MyQuestionsViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyQuestionsViewHolders holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyQuestionsViewHolders extends RecyclerView.ViewHolder {
        TextView questioncardtitle, questioncarddetails, answernums;

        public MyQuestionsViewHolders(View itemView) {
            super(itemView);
            questioncardtitle = itemView.findViewById(R.id.questioncardtitle);
            questioncarddetails = itemView.findViewById(R.id.questioncarddetails);
            answernums = itemView.findViewById(R.id.answernums);
        }
    }
}
