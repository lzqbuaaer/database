package com.example.entity;

public class Teacher extends Account{
    private String tsex;
    private String tdept;
    private String temail;

    public String getTsex() {
        return tsex;
    }

    public void setTsex(String tsex) {
        this.tsex = tsex;
    }

    public String getTdept() {
        return tdept;
    }

    public void setTdept(String tdept) {
        this.tdept = tdept;
    }

    public String getTemail() {
        return temail;
    }

    public void setTemail(String temail) {
        this.temail = temail;
    }
}
