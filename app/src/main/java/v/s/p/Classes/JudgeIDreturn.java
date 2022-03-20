package v.s.p.Classes;

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
