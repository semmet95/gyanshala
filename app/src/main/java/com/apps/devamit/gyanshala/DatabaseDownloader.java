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
    static HashMap<String, ArrayList<String>> quesMetadata=new HashMap<>(),
            quesAnswers=new HashMap<>();
    static ArrayList<String> questionTitleList=new ArrayList<>(), questionDescriptionList=new ArrayList<>();
    static MainActivity obj;
    static void refresh() {
        quesMetadata.clear();
        quesAnswers.clear();
        questionTitleList.clear();
        questionDescriptionList.clear();
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
                    Log.e("putting key :", "as "+ques[0]+" and "+ques[1]);
                    quesMetadata.put(ques[0]+" "+ques[1], metadatas);
                    if(!question.ans_id.equals("null")) {
                        for(String x: question.ans_id.split(" ")) {
                            DatabaseReference refa=mDatabase.getReference("Answers").child(x);
                            Log.e("looking for :", refa.getKey());
                            refa.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    AnswerChildren answer=dataSnapshot.getValue(AnswerChildren.class);
                                    String ans=answer.answer+"\n\n"+"- "+answer.user_id+"@gmail.com";
                                    ArrayList<String> ansList=quesAnswers.get(ques[0]+" "+ques[1]);
                                    if(ansList==null)
                                        ansList=new ArrayList<>();
                                    ansList.add(ans);
                                    quesAnswers.put(ques[0]+" "+ques[1], ansList);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }
                obj.refreshUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}