package com.example.happyfarm.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.happyfarm.LoginScreen.USERID;

public class ThongTinTaiKhoan {
    private String uID;
    private int tongTienNongTrai;
    private int expLevel;
    private int giaTriTheLuc;
    private int tongSoLuongLua;
    private int tongSoluongCachua;
    private int tongSoLuongCaRot;

    public ThongTinTaiKhoan(){}

    public ThongTinTaiKhoan(String uID,
                            int tongTienNongTrai,
                            int expLevel,
                            int giaTriTheLuc,
                            int tongSoLuongLua,
                            int tongSoluongCachua,
                            int tongSoLuongCaRot) {
        this.uID = uID;
        this.tongTienNongTrai = tongTienNongTrai;
        this.expLevel = expLevel;
        this.giaTriTheLuc = giaTriTheLuc;
        this.tongSoLuongLua = tongSoLuongLua;
        this.tongSoluongCachua = tongSoluongCachua;
        this.tongSoLuongCaRot = tongSoLuongCaRot;
    }

    public int getTongTienNongTrai() {
        return tongTienNongTrai;
    }

    public void setTongTienNongTrai(int tongTienNongTrai) {
        this.tongTienNongTrai = tongTienNongTrai;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public int getExpLevel() {
        return expLevel;
    }

    public void setExpLevel(int expLevel) {
        this.expLevel = expLevel;
    }

    public int getGiaTriTheLuc() {
        return giaTriTheLuc;
    }

    public void setGiaTriTheLuc(int giaTriTheLuc) {
        this.giaTriTheLuc = giaTriTheLuc;
    }

    public int getTongSoLuongLua() {
        return tongSoLuongLua;
    }

    public void setTongSoLuongLua(int tongSoLuongLua) {
        this.tongSoLuongLua = tongSoLuongLua;
    }

    public int getTongSoluongCachua() {
        return tongSoluongCachua;
    }

    public void setTongSoluongCachua(int tongSoluongCachua) {
        this.tongSoluongCachua = tongSoluongCachua;
    }

    public int getTongSoLuongCaRot() {
        return tongSoLuongCaRot;
    }

    public void setTongSoLuongCaRot(int tongSoLuongCaRot) {
        this.tongSoLuongCaRot = tongSoLuongCaRot;
    }

    public void Create(){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        db.collection("ThongTinNongTrai")
                .add(this)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }

    public void Update(){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        db.collection("ThongTinNongTrai")
                .document(USERID)
                .update("tongTienNongTrai",this.getTongTienNongTrai(),
                        "expLevel", this.getExpLevel(),
                        "giaTriTheLuc", this.getGiaTriTheLuc(),
                        "tongSoLuongLua", this.getTongSoLuongLua(),
                        "tongSoLuongCaChua", this.getTongSoluongCachua(),
                        "tongSoLuongCaRot", this.getTongSoLuongCaRot())//sửa lại uid trong thông tin đăng nhập
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
