package com.apps.devamit.gyanshala;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AnswerActicity extends AppCompatActivity {
    TextView answerquestiontitle, answerquestiondetails;
    EditText submittedanswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_acticity);
        answerquestiontitle=findViewById(R.id.answerquestiontitle);
        answerquestiondetails=findViewById(R.id.answerquestiondetails);
        submittedanswer=findViewById(R.id.submittedanswer);
        Intent intent=getIntent();
        answerquestiontitle.setText(intent.getStringExtra("title"));
        answerquestiondetails.setText(intent.getStringExtra("details"));
    }

    public void submitClicked(View v) {
        FirebaseDatabase mDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myref=mDatabase.getReference("Answers"), myref2;
        ArrayList<String> metadata= DatabaseDownloader.quesMetadata.get(answerquestiontitle.getText().toString()+
                " "+answerquestiondetails.getText().toString());
        //Log.e("tryna add :", "metadata = "+metadata.get(0)+" "+metadata.get(1)+" "+metadata.get(2));
        String user_id= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        user_id=user_id.substring(0, user_id.indexOf('@'));
        AnswerChildren ans=new AnswerChildren(submittedanswer.getText().toString(), user_id);
        String aid=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        aid=aid.replace(".","");
        myref.child(aid).setValue(ans);
        if(metadata.get(2).equals("null")) {
            metadata.remove(2);
            metadata.add(2, aid);
        }
        else
            metadata.add(2, metadata.get(2)+" "+aid);
        QuestionChildren ques=new QuestionChildren(metadata.get(2), metadata.get(1), metadata.get(0),
                answerquestiontitle.getText().toString(), answerquestiondetails.getText().toString());
        myref2=mDatabase.getReference("Questions");
        //Log.e("tryna add :", "to database = "+ques.ans_id+" "+ques.q_details+" "+ques.q_id+" "+ques.q_title+" "+ques.user_id);
        myref2.child(metadata.get(0)).setValue(ques);
        finish();
    }
}
