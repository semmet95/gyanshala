package com.apps.devamit.gyanshala;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
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

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    public void answerclicked(View v) {
        Intent intent=new Intent(this, AnswerActicity.class);
        intent.putExtra("title", title);
        intent.putExtra("details", details);
        startActivityForResult(intent, 11);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==11) {
            allAnswers.setAdapter(new QuestionsWithAnswersAdapter(DatabaseDownloader.quesAnswers.get(title + " " + details)));
        }
    }
}
