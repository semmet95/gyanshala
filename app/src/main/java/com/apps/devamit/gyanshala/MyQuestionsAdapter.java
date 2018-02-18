package com.apps.devamit.gyanshala;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyQuestionsAdapter extends RecyclerView.Adapter<MyQuestionsAdapter.MyQuestionsViewHolders> {

    @Override
    public MyQuestionsViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.questioncard, parent, false);
        return new MyQuestionsViewHolders(view);
    }

    @Override
    public void onBindViewHolder(final MyQuestionsViewHolders holder, int position) {
        holder.questioncardtitle.setText(DatabaseDownloader.myQuestionTitleList.get(position));
        holder.questioncarddetails.setText(DatabaseDownloader.myQuestionDescriptionList.get(position));
        String ans_id=DatabaseDownloader.quesMetadata.get(DatabaseDownloader.myQuestionTitleList.get(position)+
                " "+DatabaseDownloader.myQuestionDescriptionList.get(position)).get(2);
        if(ans_id.equals("null"))
            ans_id="0 answers";
        else
            ans_id=ans_id.split(" ").length+" answers";
        holder.answernums.setText(ans_id);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), QuestionWithAnswers.class);
                intent.putExtra("title", DatabaseDownloader.myQuestionTitleList.get(holder.getAdapterPosition()));
                intent.putExtra("details", DatabaseDownloader.myQuestionDescriptionList.get(holder.getAdapterPosition()));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //Log.e("my questions :", "in adapter with myquestiontitle list size = "+DatabaseDownloader.myQuestionTitleList.size());
        return DatabaseDownloader.myQuestionTitleList.size();
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
