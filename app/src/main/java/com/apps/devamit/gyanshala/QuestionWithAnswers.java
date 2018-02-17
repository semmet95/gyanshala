package com.apps.devamit.gyanshala;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionWithAnswers extends AppCompatActivity {
    TextView questionTitleWithAnswers, questionDetailsWithAnswers;
    RecyclerView allAnswers;
    ArrayList<String> ans;
    String title, details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_with_answers);
        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        details=intent.getStringExtra("details");
        questionTitleWithAnswers=findViewById(R.id.questionTitleWithAnswers);
        questionDetailsWithAnswers=findViewById(R.id.questionDetailsWithAnswers);
        allAnswers=findViewById(R.id.allAnswers);
        questionTitleWithAnswers.setText(title);
        questionDetailsWithAnswers.setText(details);
        ans=DatabaseDownloader.quesAnswers.get(title+" "+details);
        allAnswers.setLayoutManager(new LinearLayoutManager(this));
        allAnswers.setAdapter(new QuestionsWithAnswersAdapter(ans));
    }

    public void answerclicked(View v) {
        Intent intent=new Intent(this, AnswerActicity.class);
        intent.putExtra("title", title);
        intent.putExtra("details", details);
        startActivity(intent);
    }
}
