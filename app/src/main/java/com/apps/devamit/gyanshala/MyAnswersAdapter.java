package com.apps.devamit.gyanshala;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAnswersAdapter extends RecyclerView.Adapter<MyAnswersAdapter.MyAnswersViewHolders> {

    @Override
    public MyAnswersViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.questioncard, parent, false);
        return new MyAnswersAdapter.MyAnswersViewHolders(view);
    }

    @Override
    public void onBindViewHolder(final MyAnswersViewHolders holder, int position) {
        holder.questioncardtitle.setText(DatabaseDownloader.questionsIAnswered.get(position).q_title);
        holder.questioncarddetails.setText(DatabaseDownloader.questionsIAnswered.get(position).q_details);
        String ans_id=DatabaseDownloader.questionsIAnswered.get(position).ans_id;
        if(ans_id.equals("null"))
            ans_id="0 answers";
        else
            ans_id=ans_id.split(" ").length+" answers";
        holder.answernums.setText(ans_id);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), QuestionWithAnswers.class);
                intent.putExtra("title", DatabaseDownloader.questionsIAnswered.get(holder.getAdapterPosition()).q_title);
                intent.putExtra("details", DatabaseDownloader.questionsIAnswered.get(holder.getAdapterPosition()).q_details);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return DatabaseDownloader.questionsIAnswered.size();
    }

    class MyAnswersViewHolders extends RecyclerView.ViewHolder {
        TextView questioncardtitle, questioncarddetails, answernums;

        public MyAnswersViewHolders(View itemView) {
            super(itemView);
            questioncardtitle = itemView.findViewById(R.id.questioncardtitle);
            questioncarddetails = itemView.findViewById(R.id.questioncarddetails);
            answernums = itemView.findViewById(R.id.answernums);
        }
    }
}