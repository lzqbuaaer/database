package com.example.entity;

public class Course {
    private Integer id;
    private String cno;
    private String tno;
    private String cname;
    private String ccredit;
    private String cdescribe;
    private Integer cday;
    private Integer ctime;
    private String cclassroom;

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", cno='" + cno + '\'' +
                ", tno='" + tno + '\'' +
                ", cname='" + cname + '\'' +
                ", ccredit=" + ccredit +
                ", cdescribe='" + cdescribe + '\'' +
                ", cday='" + cday + '\'' +
                ", ctime='" + ctime + '\'' +
                ", cclassroom='" + cclassroom + '\'' +
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

    public Integer getCday() {
        return cday;
    }
    public void setCday(Integer cday) {
        this.cday = cday;
    }
    public Integer getCtime() {
        return ctime;
    }
    public void setCtime(Integer ctime) {
        this.ctime = ctime;
    }

    public void setCclassroom(String cclassroom) {
        this.cclassroom = cclassroom;
    }

    public String getCclassroom() {
        return cclassroom;
    }

}
