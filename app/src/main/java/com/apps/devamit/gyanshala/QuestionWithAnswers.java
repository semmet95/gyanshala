package com.apps.devamit.gyanshala;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class QuestionWithAnswers extends AppCompatActivity {
    TextView questionTitleWithAnswers, questionDetailsWithAnswers;
    RecyclerView allAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_with_answers);
        Intent intent=getIntent();
        String title=intent.getStringExtra("title"), details=intent.getStringExtra("details");
        questionTitleWithAnswers=findViewById(R.id.questionTitleWithAnswers);
        questionDetailsWithAnswers=findViewById(R.id.questionDetailsWithAnswers);
        allAnswers=findViewById(R.id.allAnswers);
        questionTitleWithAnswers.setText(title);
        questionDetailsWithAnswers.setText(details);
    }
}
