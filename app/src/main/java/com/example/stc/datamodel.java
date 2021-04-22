package com.example.stc;

public class datamodel {
    String ins_name,degree,deadline,test,logo;
    datamodel(){

    }

    public datamodel(String ins_name, String degree, String deadline, String test, String logo) {
        this.ins_name = ins_name;
        this.degree = degree;
        this.deadline = deadline;
        this.test = test;
        this.logo = logo;
    }
    public String getIns_name() {
        return ins_name;
    }

    public void setIns_name(String ins_name) {
        this.ins_name = ins_name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}
