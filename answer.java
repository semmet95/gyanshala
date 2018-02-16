package com.example.adi.firepro;

/**
 * Created by adi on 2/17/2018.
 */

public class answer {
    String ansid;
    String ans;
    String qid;

    public answer() {
    }

    public answer(String ansid, String ans, String qid) {
        this.ansid = ansid;
        this.ans = ans;
        this.qid = qid;
    }

    public String getAnsid() {
        return ansid;
    }

    public String getAns() {
        return ans;
    }

    public String getQid() {
        return qid;
    }
}