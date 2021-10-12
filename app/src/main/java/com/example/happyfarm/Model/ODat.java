package com.example.happyfarm.Model;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.happyfarm.LoginScreen.CACHUA;
import static com.example.happyfarm.LoginScreen.CAROT;
import static com.example.happyfarm.LoginScreen.FARMCOIN;
import static com.example.happyfarm.LoginScreen.LUA;
import static com.example.happyfarm.LoginScreen.STAMINA;
import static com.example.happyfarm.LoginScreen.USERID;
import static com.example.happyfarm.WaitActivity.O_DAT_UNLOCKED;

public class ODat {
    private String uID;         //uid của player
    private int oDatID;         //11,12,13,14 - lúa; 21,22,23,24 - cà chua; 31,32,33,34 - cà rốt
    private boolean moKhoa;
    private boolean lamDat;
    private boolean gieoHat;
    private boolean tuoiNuoc;
    private boolean bonPhan;
    private int soNgayThuHoach;
    private int sanLuongThuHoach;

    public ODat() { }

    //dùng để xử lý dữ liệu lên database lúc khởi tạo tài khoản mới
    public ODat(String uID,
                int oDatID,
                boolean moKhoa,
                boolean lamDat,
                boolean gieoHat,
                boolean tuoiNuoc,
                boolean bonPhan,
                int soNgayThuHoach,
                int sanLuongThuHoach)
    {
        this.uID = uID;
        this.oDatID = oDatID;
        this.moKhoa = moKhoa;
        this.lamDat = lamDat;
        this.gieoHat = gieoHat;
        this.tuoiNuoc = tuoiNuoc;
        this.bonPhan = bonPhan;
        this.soNgayThuHoach = soNgayThuHoach;
        this.sanLuongThuHoach = sanLuongThuHoach;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public int getoDatID() {
        return oDatID;
    }

    public void setoDatID(int oDatID) {
        this.oDatID = oDatID;
    }

    public boolean isMoKhoa() {
        return moKhoa;
    }

    public void setMoKhoa(boolean moKhoa) {
        this.moKhoa = moKhoa;
    }

    public boolean isLamDat() {
        return lamDat;
    }

    public void setLamDat(boolean lamDat) {
        this.lamDat = lamDat;
    }

    public boolean isGieoHat() {
        return gieoHat;
    }

    public void setGieoHat(boolean gieoHat) {
        this.gieoHat = gieoHat;
    }

    public boolean isTuoiNuoc() {
        return tuoiNuoc;
    }

    public void setTuoiNuoc(boolean tuoiNuoc) {
        this.tuoiNuoc = tuoiNuoc;
    }

    public boolean isBonPhan() {
        return bonPhan;
    }

    public void setBonPhan(boolean bonPhan) {
        this.bonPhan = bonPhan;
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

        this.setuID(USERID);
        this.setMoKhoa(false);
        this.setLamDat(false);
        this.setGieoHat(false);
        this.setTuoiNuoc(false);
        this.setBonPhan(false);
        this.setSoNgayThuHoach(0);
        this.setSanLuongThuHoach(0);

        String od_id = String.valueOf(this.getoDatID());
        db.collection("ODat").document(USERID)
                .collection("ODatID").document(od_id)
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

    //mở khóa ô đất với điều kiện farmcoin hiện có > farmcoin tiêu hao
    public void MoKhoa(){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        String od_id = String.valueOf(this.getoDatID());
        int od_id_ = this.getoDatID();
        db.collection("ODat").document(USERID)
                .collection("ODatID").document(od_id)
                .update("moKhoa",true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (od_id_>11) FARMCOIN-=100*(O_DAT_UNLOCKED+1);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: " + e.getMessage());
                    }
                });
        //update thông tin nông trại sau khi func này chạy xong
    }

    //làm đất với điều kiện moKhoa=true, các thuộc tính còn lại = false
    public void LamDat(){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        String od_id = String.valueOf(this.getoDatID());
        db.collection("ODat").document(USERID)
                .collection("ODatID").document(od_id)
                .update("lamDat",true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        STAMINA-=10;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: " + e.getMessage());
                    }
                });
        //update thông tin nông trại sau khi func này chạy xong
    }

    public void GieoHat(){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        String od_id = String.valueOf(this.getoDatID());
        db.collection("ODat").document(USERID)
                .collection("ODatID").document(od_id)
                .update("gieoHat",true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        STAMINA-=10;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: " + e.getMessage());
                    }
                });
        //update thông tin nông trại sau khi func này chạy xong
    }

    public void TuoiNuoc(){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        int sanLuongMoi = this.getSanLuongThuHoach(); //thiếu công thức tính sản lượng sau khi tưới nước
        String od_id = String.valueOf(this.getoDatID());
        db.collection("ODat").document(USERID)
                .collection("ODatID").document(od_id)
                .update("tuoiNuoc",true,
                        "sanLuongThuHoach",sanLuongMoi)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        STAMINA-=10;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: " + e.getMessage());
                    }
                });
        //update thông tin nông trại sau khi func này chạy xong
    }

    public void BonPhan(){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        String od_id = String.valueOf(this.getoDatID());
        db.collection("ODat").document(USERID)
                .collection("ODatID").document(od_id)
                .update("bonPhan",true, "soNgayThuHoach", this.getSoNgayThuHoach()+1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        STAMINA-=10;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: " + e.getMessage());
                    }
                });
        //update thông tin nông trại sau khi func này chạy xong
    }

    public void ThuHoach(){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        int id = this.getoDatID();
        int sanluong = this.getSanLuongThuHoach();
        String od_id = String.valueOf(this.getoDatID());
        db.collection("ODat").document(USERID)
                .collection("ODatID").document(od_id)
                .update("lamDat", false,
                        "gieoHat",false,
                        "tuoiNuoc", false,
                        "bonPhan", false,
                        "soNgayThuHoach",0,
                        "sanLuongThuHoach",0)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        STAMINA-=10;
                        if (id<20) LUA += sanluong;
                        if (id>20 && id<30) CACHUA += sanluong;
                        if (id>30 && id<40) CAROT += sanluong;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: " + e.getMessage());
                    }
                });
        //update thông tin nông trại sau khi func này chạy xong
    }
}
