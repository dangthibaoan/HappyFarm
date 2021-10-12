package com.example.happyfarm.Model;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.happyfarm.LoginScreen.USERID;

public class TaiKhoan {
    private String uid;
    private String usn;
    private String pwd;

    public TaiKhoan() {
    }

    public TaiKhoan(String uid, String usn, String pwd){
        this.uid = uid;
        this.usn = usn;
        this.pwd = pwd;
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

    public String Register(){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        db.collection("User")
                .add(this)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        USERID = documentReference.getId();//lấy uid tự động từ firebase
                        Log.d("TAG", "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        USERID = "Fail";
                        Log.w("TAG", "Error adding document", e);
                    }
                });
        return USERID;
    }

    public void Update(){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        db.collection("User")
                .document(this.getUid())
                .update("uid", this.getUid(),
                        "usn", this.getUsn(), "pwd", this.getPwd())//sửa lại uid trong thông tin đăng nhập
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "onSuccess: Add role success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: " + e.getMessage());
                    }
                });
    }
}

