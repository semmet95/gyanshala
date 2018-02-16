package com.example.adi.firepro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adi on 2/13/2018.
 */

public class aditya {
    String qid;
    String ques;
    String id;
    String ans_id;


    public aditya() {
    }

    public aditya(String qid, String ques, String id,String a) {
        ans_id=a;
        this.qid = qid;
        this.ques = ques;
        this.id = id;

    }

    public String getId() {
        return id;
    }

    public String getQues() {
        return ques;
    }

    public String getAns_id() {
        return ans_id;
    }

    public String getQid() {
        return qid;
    }
}