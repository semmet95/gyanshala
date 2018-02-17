package com.apps.devamit.gyanshala;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionsWithAnswersAdapter extends RecyclerView.Adapter<QuestionsWithAnswersAdapter.QuestionWithAnswersViewHolder> {
    private ArrayList<String> thisQuestionAnswers;

    QuestionsWithAnswersAdapter(ArrayList<String> ans) {
        thisQuestionAnswers=ans;
    }

    class QuestionWithAnswersViewHolder extends RecyclerView.ViewHolder {
        TextView answertext;

        public QuestionWithAnswersViewHolder(View itemView) {
            super(itemView);
            answertext=itemView.findViewById(R.id.answertext);
        }
    }

    @Override
    public QuestionWithAnswersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.questionwithanswercard, parent, false);
        return new QuestionWithAnswersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionWithAnswersViewHolder holder, int position) {
        holder.answertext.setText(thisQuestionAnswers.get(position));
    }

    @Override
    public int getItemCount() {
        //Log.e("questionansweradapter :", "number of answers = "+thisQuestionAnswers.size());
        if(thisQuestionAnswers==null)
            return 0;
        return thisQuestionAnswers.size();
    }
}