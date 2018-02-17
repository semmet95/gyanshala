package com.example.adi.jsoupwa;

/**
 * Created by adi on 2/17/2018.
 */

public class Scholarship {
    String scholarship;
    String dueDate;

    public Scholarship(String scholarship, String dueDate) {
        this.scholarship = scholarship;
        this.dueDate = dueDate;
    }

    public String getScholarship() {
        return scholarship;
    }

    public String getDueDate() {
        return dueDate;
    }
}

