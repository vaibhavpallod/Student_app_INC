package com.example.studentappinc;

public class JudgeIDreturn {

    String studentID,projecttitle;

    public JudgeIDreturn(String id, String labloc) {
    this.projecttitle=labloc;
    this.studentID=id;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getProjecttitle() {
        return projecttitle;
    }
}
