package com.example.happyfarm.Model;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.example.happyfarm.LoginScreen.USERID;

public class RuongNongSan {
    private String uID;
    private int nongSanID; //1-lua, 2-cachua, 3carot
    private int levelHatGiong;
    private int soNgayThuHoach;
    private int sanLuongThuHoach;

    public RuongNongSan(){}

    public RuongNongSan(String uID, int nongSanID, int levelHatGiong, int soNgayThuHoach, int sanLuongThuHoach) {
        this.uID = uID;
        this.nongSanID = nongSanID;
        this.levelHatGiong = levelHatGiong;
        this.soNgayThuHoach = soNgayThuHoach;
        this.sanLuongThuHoach = sanLuongThuHoach;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public int getNongSanID() {
        return nongSanID;
    }

    public void setNongSanID(int nongSanID) {
        this.nongSanID = nongSanID;
    }

    public int getLevelHatGiong() {
        return levelHatGiong;
    }

    public void setLevelHatGiong(int levelHatGiong) {
        this.levelHatGiong = levelHatGiong;
    }

    public int getSoNgayThuHoach() {
        return soNgayThuHoach;
    }

    public void setSoNgayThuHoach(int soNgayThuHoach) {
        this.soNgayThuHoach = soNgayThuHoach;
    }

    public int getSanLuongThuHoach() {
        return sanLuongThuHoach;
    }

    public void setSanLuongThuHoach(int sanLuongThuHoach) {
        this.sanLuongThuHoach = sanLuongThuHoach;
    }

    public void Create(){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        String ns_id = String.valueOf(this.getNongSanID());
        db.collection("RuongNongSan").document(USERID)
                .collection("NongSanID").document(ns_id)
                .set(this)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "onSuccess: Add success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: " + e.getMessage());
                    }
                });
    }

    public void Update(){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        String ns_id = String.valueOf(this.getNongSanID());
        db.collection("RuongNongSan").document(USERID)
                .collection("NongSanID").document(ns_id)
                .update("soNgayThuHoach",this.getSoNgayThuHoach(),
                        "sanLuongThuHoach",this.getSanLuongThuHoach())
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
