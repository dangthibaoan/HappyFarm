package com.example.happyfarm.Model;

public class User {
    private String uid;
    private String accName;
    private String accPass;

    public User() {
    }

    public User(String accName, String accPass){
        this.accName=accName;
        this.accPass=accPass;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccPass() {
        return accPass;
    }

    public void setAccPass(String accPass) {
        this.accPass = accPass;
    }
}

