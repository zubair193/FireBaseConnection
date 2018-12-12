package com.example.muhammadzubair.fireapp;

public class Users {
    private String Name,Age,Dept,u_id;

    public Users() {
    }

    public Users(String age, String dept, String name, String u_id) {
        Age = age;
        Dept = dept;
        Name = name;
        this.u_id = u_id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getDept() {
        return Dept;
    }

    public void setDept(String dept) {
        Dept = dept;
    }
}
