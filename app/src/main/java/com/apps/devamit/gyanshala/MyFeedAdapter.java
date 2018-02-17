package com.apps.devamit.gyanshala;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyFeedAdapter extends RecyclerView.Adapter<MyFeedViewHolders> {

    @Override
    public MyFeedViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.questioncard, parent, false);
        return new MyFeedViewHolders(view);
    }

    @Override
    public void onBindViewHolder(final MyFeedViewHolders holder, final int position) {
        holder.questioncardtitle.setText(DatabaseDownloader.questionTitleList.get(position));
        holder.questioncarddetails.setText(DatabaseDownloader.questionDescriptionList.get(position));
        holder.answernums.setText(DatabaseDownloader.quesMetadata.get(new String[]
                {DatabaseDownloader.questionTitleList.get(position), DatabaseDownloader.questionDescriptionList.get(position)})
                .get(2).split(" ").length);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), QuestionWithAnswers.class);
                intent.putExtra("title", DatabaseDownloader.questionTitleList.get(position));
                intent.putExtra("details", DatabaseDownloader.questionDescriptionList.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //DatabaseDownloader.refresh();
        return DatabaseDownloader.questionTitleList.size();
    }
}
