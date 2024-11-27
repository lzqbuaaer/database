package com.example.entity;

public class StudentCourse {
    private Integer scId;
    private String sno;
    private String sname;
    private String cno;
    private String cname;
    private String tno;
    private String ccredit;
    private String cdescribe;
    private double grade;

    @Override
    public String toString() {
        return "StudentCourse{" +
                "scId=" + scId +
                ", sno='" + sno + '\'' +
                ", sname='" + sname + '\'' +
                ", cno='" + cno + '\'' +
                ", cname='" + cname + '\'' +
                ", tno='" + tno + '\'' +
                ", ccredit='" + ccredit + '\'' +
                ", cdescribe='" + cdescribe + '\'' +
                ", grade=" + grade +
                '}';
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getTno() {
        return tno;
    }

    public void setTno(String tno) {
        this.tno = tno;
    }

    public String getCcredit() {
        return ccredit;
    }

    public void setCcredit(String ccredit) {
        this.ccredit = ccredit;
    }

    public String getCdescribe() {
        return cdescribe;
    }

    public void setCdescribe(String cdescribe) {
        this.cdescribe = cdescribe;
    }
    public Integer getScId() {
        return scId;
    }

    public void setScId(Integer scId) {
        this.scId = scId;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
