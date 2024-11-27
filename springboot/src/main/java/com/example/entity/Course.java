package com.example.entity;

public class Course {
    private Integer id;
    private String cno;
    private String tno;
    private String cname;
    private double ccredit;
    private String cdescribe;

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", cno='" + cno + '\'' +
                ", tno='" + tno + '\'' +
                ", cname='" + cname + '\'' +
                ", ccredit=" + ccredit +
                ", cdescribe='" + cdescribe + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getTno() {
        return tno;
    }

    public void setTno(String tno) {
        this.tno = tno;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public double getCcredit() {
        return ccredit;
    }

    public void setCcredit(float ccredit) {
        this.ccredit = ccredit;
    }

    public String getCdescribe() {
        return cdescribe;
    }

    public void setCdescribe(String cdescribe) {
        this.cdescribe = cdescribe;
    }
}
