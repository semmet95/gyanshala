package com.apps.devamit.gyanshala;

class QuestionChildren {
    public String ans_id, user_id, q_id, q_title, q_details;

    public QuestionChildren(){}

    public QuestionChildren(String ai, String ui, String qi, String title, String details) {
        ans_id=ai;
        user_id=ui;
        q_id=qi;
        q_title=title;
        q_details=details;
    }
}