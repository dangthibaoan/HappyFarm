package com.example.happyfarm.Model;

public class TaiKhoan {
    private String uid;
    private String usn;
    private String pwd;

    public TaiKhoan() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void Register(String usn, String pwd){
        this.setUid(System.currentTimeMillis() +usn);
        this.setUsn(usn);
        this.setPwd(pwd);
    }

//    public void Update(String usn, String pwd){
//        this.setUsn(usn);
//        this.setPwd(pwd);
//   }
}

