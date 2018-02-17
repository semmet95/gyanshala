package com.apps.devamit.gyanshala;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

class DatabaseDownloader {
     static HashMap<String[], ArrayList<String>> quesMetadata=new HashMap<>(),
                                                quesAnswers=new HashMap<>();
     static ArrayList<String> questionTitleList=new ArrayList<>(), questionDescriptionList=new ArrayList<>();
     static void refresh() {
         final FirebaseDatabase mDatabase=FirebaseDatabase.getInstance();
         DatabaseReference refq=mDatabase.getReference("Questions");
         refq.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                     QuestionChildren question=snapshot.getValue(QuestionChildren.class);
                     Log.e("in question :", snapshot.getKey());
                     final String[] ques=new String[2];
                     ques[0]=question.q_title;
                     ques[1]=question.q_details;
                     questionTitleList.add(ques[0]);
                     questionDescriptionList.add(ques[1]);
                     ArrayList<String> metadatas=new ArrayList<>();
                     metadatas.add(question.q_id);
                     metadatas.add(question.user_id);
                     metadatas.add(question.ans_id);
                     quesMetadata.put(ques, metadatas);
                     if(!question.ans_id.equals("null")) {
                         for(String x: question.ans_id.split(" ")) {
                             DatabaseReference refa=mDatabase.getReference("Answers").child(x);
                             Log.e("looking for :", refa.getKey());
                             refa.addValueEventListener(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(DataSnapshot dataSnapshot) {
                                     for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                         AnswerChildren answer=snapshot.getValue(AnswerChildren.class);
                                         String ans=answer.answer+"\n\n"+"- "+answer.user_id+"@gmail.com";
                                         ArrayList<String> ansList=quesAnswers.get(ques);
                                         if(ansList==null)
                                             ansList=new ArrayList<>();
                                         ansList.add(ans);
                                         quesAnswers.put(ques, ansList);
                                     }
                                 }

                                 @Override
                                 public void onCancelled(DatabaseError databaseError) {

                                 }
                             });
                         }
                     }
                 }
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });
     }
}