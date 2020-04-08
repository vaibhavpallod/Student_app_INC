package com.example.studentappinc;

public class JudgeIDreturn {

    String studentID,lablocation;

    public JudgeIDreturn(String id, String labloc) {
    this.lablocation=labloc;
    this.studentID=id;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getLablocation() {
        return lablocation;
    }
}
