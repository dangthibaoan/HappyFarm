package com.example.happyfarm.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.happyfarm.LoginScreen.USERID;

public class SanPham {
    private String uid;
    private int sanPhamID;
    private String tenSanPham;          //hạt giống cấp n, nước thần x2 sản lượng thu hoạch, nước tăng lực +50 giá trị thể lực
    private String anhMinhHoa;
    private String moTaSanPham;
    private int donGia;
    private boolean trangThaiSanPham;

    public SanPham(){}

    public SanPham(String uid, int sanPhamID, String tenSanPham, String anhMinhHoa, String moTaSanPham, int donGia, boolean trangThaiSanPham) {
        this.uid = uid;
        this.sanPhamID = sanPhamID;
        this.tenSanPham = tenSanPham;
        this.anhMinhHoa = anhMinhHoa;
        this.moTaSanPham = moTaSanPham;
        this.donGia = donGia;
        this.trangThaiSanPham = trangThaiSanPham;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getSanPhamID() {
        return sanPhamID;
    }

    public void setSanPhamID(int sanPhamID) {
        this.sanPhamID = sanPhamID;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getAnhMinhHoa() {
        return anhMinhHoa;
    }

    public void setAnhMinhHoa(String anhMinhHoa) {
        this.anhMinhHoa = anhMinhHoa;
    }

    public String getMoTaSanPham() {
        return moTaSanPham;
    }

    public void setMoTaSanPham(String moTaSanPham) {
        this.moTaSanPham = moTaSanPham;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public boolean isTrangThaiSanPham() {
        return trangThaiSanPham;
    }

    public void setTrangThaiSanPham(boolean trangThaiSanPham) {
        this.trangThaiSanPham = trangThaiSanPham;
    }

    public void Create(){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        String sp_id = String.valueOf(this.getSanPhamID());
        db.collection("Shop").document(USERID)
                .collection("SanPhamID").document(sp_id)
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

    public void Mua(){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        String sp_id = String.valueOf(this.getSanPhamID());
        db.collection("Shop").document(USERID)
                .collection("SanPhamID").document(sp_id)
                .update("trangThaiSanPham",true)//sửa lại trạng thái sản phẩm, true: đã mua, false: chưa mua
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
