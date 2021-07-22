package v.s.p;

public class Calculate {
    String id;
    int m1,m2,m3,m4,m5,m6,m7,total;



    public Calculate(String id, int m1, int m2, int m3, int m4, int m5, int m6, int m7, int total) {
        this.id = id;
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
        this.m4 = m4;
        this.m5 = m5;
        this.m6 = m6;
        this.m7 = m7;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public int getTotal() {
        return total;
    }

    public int getM1() {
        return m1;
    }

    public int getM2() {
        return m2;
    }

    public int getM3() {
        return m3;
    }

    public int getM4() {
        return m4;
    }

    public int getM5() {
        return m5;
    }

    public int getM6() {
        return m6;
    }

    public int getM7() {
        return m7;
    }
}
