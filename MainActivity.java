package com.example.adi.firepro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myref;
    EditText question,uid;
    String id,ques,qid;
    Map<String ,List> map=new Hashtable<String, List>();
    List<String> list=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uid=(EditText)findViewById(R.id.uid);
        question=(EditText)findViewById(R.id.question);
        database=FirebaseDatabase.getInstance();
        myref=database.getReference("Questions");

    }


    public void ask(View v){


        if(!question.getText().toString().equals(null)){
            ques=question.getText().toString();
        }

        if(!uid.getText().toString().equals(null)){
            id=uid.getText().toString();
        }

        qid= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        qid=qid.replace(".","");
        String ans_id="null";
        aditya adi=new aditya(qid,ques,id,ans_id);
        myref.child(qid).setValue(adi);

        Toast.makeText(this,"Posted" ,Toast.LENGTH_LONG).show();
        return;

    }

    public void Go_to_ques(View view){
        Intent i=new Intent(this,question.class);
        startActivity(i);

    }
}