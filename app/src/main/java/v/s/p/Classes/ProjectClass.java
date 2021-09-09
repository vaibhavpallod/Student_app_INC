package v.s.p.Classes;

public class ProjectClass {
    String id, title, abstr, college, domain, email, name, mobno, projecttype;

    public ProjectClass(String id, String title, String abstr, String college, String domain, String email, String name, String mobno, String projecttype) {
        this.id = id;
        this.title = title;
        this.abstr = abstr;
        this.college = college;
        this.domain = domain;
        this.email = email;
        this.name = name;
        this.mobno = mobno;
        this.projecttype = projecttype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstr() {
        return abstr;
    }

    public void setAbstr(String abstr) {
        this.abstr = abstr;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobno() {
        return mobno;
    }

    public void setMobno(String mobno) {
        this.mobno = mobno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProjecttype() {
        return projecttype;
    }

    public void setProjecttype(String projecttype) {
        this.projecttype = projecttype;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
