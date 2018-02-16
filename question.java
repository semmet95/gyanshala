package com.example.adi.firepro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class question extends AppCompatActivity {
    String oldanswerid;
    final String TAG="Aditya";
    static String answer1="efrg";
    String quesid,answerid;
    FirebaseDatabase database;
    DatabaseReference myref,myref2;
    List<aditya> list=new ArrayList<aditya>();
    static EditText ans1,ans2;
    TextView ques1,ques2;
    static String ans_id,ans;
    Button button1,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        database=FirebaseDatabase.getInstance();
        myref=database.getReference("Questions");
        myref2=database.getReference("Answers");

        ans1=(EditText)findViewById(R.id.ans1);
        ans2=(EditText)findViewById(R.id.ans2);
        ques1=(TextView)findViewById(R.id.question1);
        ques2=(TextView)findViewById(R.id.question2);
        button1=(Button)findViewById(R.id.button2);
        button2=(Button)findViewById(R.id.button4);


        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot a:dataSnapshot.getChildren()){
                    aditya adi=a.getValue(aditya.class);
                    ques1.setText(adi.getQues());
                    quesid=adi.getQid();
                    oldanswerid=adi.getAns_id();



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
    public void Onclick(View view){
        answer1=ans1.getText().toString();
        answerid=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()).replace(".","");
        answer aa=new answer(answerid,answer1,quesid);
        myref2.child(answerid).setValue(aa);

        if(!oldanswerid.equals("null"))
        {
            answerid=answerid+","+oldanswerid;
        }
        myref.child(quesid).child("ans_id").setValue(answerid);



    }
}
