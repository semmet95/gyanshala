package com.apps.devamit.gyanshala;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddQuestionActivity extends AppCompatActivity {
    EditText questionTitle, questionDetails;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        questionTitle= findViewById(R.id.questionTitle);
        questionDetails=findViewById(R.id.questionDetails);
        addButton=findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValid()) {
                    String title=questionTitle.getText().toString(), details=questionDetails.getText().toString();
                    FirebaseDatabase mDatabase=FirebaseDatabase.getInstance();
                    DatabaseReference myref=mDatabase.getReference("Questions");
                    String qid=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                    qid=qid.replace(".","");
                    String user_id=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    user_id=user_id.substring(0, user_id.indexOf('@'));
                    myref.child(qid).setValue(new QuestionChildren("null", user_id, qid, title, details));
                    DatabaseDownloader.refresh();
                    finish();
                }
            }
        });

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    private boolean checkValid() {
        String title=questionTitle.getText().toString(), details=questionDetails.getText().toString();
        if(title.length()<2) {
            questionTitle.setHintTextColor(Color.RED);
            questionTitle.setHint("Add valid question title");
            return false;
        }
        if(details.length()<=8) {
            questionDetails.setHintTextColor(Color.RED);
            questionDetails.setHint("Add valid question details");
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
