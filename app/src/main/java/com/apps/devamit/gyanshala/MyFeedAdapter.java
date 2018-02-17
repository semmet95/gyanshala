package com.apps.devamit.gyanshala;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyFeedAdapter extends RecyclerView.Adapter<MyFeedViewHolders> {

    @Override
    public MyFeedViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("oncreateholder :", "here in");
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.questioncard, parent, false);
        return new MyFeedViewHolders(view);
    }

    @Override
    public void onBindViewHolder(final MyFeedViewHolders holder, int position) {
        Log.e("onBind :", "here in");
        holder.questioncardtitle.setText(DatabaseDownloader.questionTitleList.get(position));
        holder.questioncarddetails.setText(DatabaseDownloader.questionDescriptionList.get(position));
        Log.e("feedadapter :", "getting with key "+DatabaseDownloader.questionTitleList.get(position)+" and "+DatabaseDownloader.questionDescriptionList.get(position));
        holder.answernums.setText(DatabaseDownloader.quesMetadata.get(DatabaseDownloader.questionTitleList.get(position)+
                " "+DatabaseDownloader.questionDescriptionList.get(position))
                .get(2).split(" ").length+" answers");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), QuestionWithAnswers.class);
                intent.putExtra("title", DatabaseDownloader.questionTitleList.get(holder.getAdapterPosition()));
                intent.putExtra("details", DatabaseDownloader.questionDescriptionList.get(holder.getAdapterPosition()));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //DatabaseDownloader.refresh();
        //Log.e("item count :", ""+DatabaseDownloader.questionTitleList.size());
        Log.e("questionTitlesize :", " = "+DatabaseDownloader.questionTitleList.size());
        return DatabaseDownloader.questionTitleList.size();
    }
}
