package com.example.happyfarm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happyfarm.Model.ODat;
import com.example.happyfarm.Model.RuongNongSan;
import com.example.happyfarm.Model.SanPham;
import com.example.happyfarm.Model.ThongTinTaiKhoan;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Objects;

import static com.example.happyfarm.HappyFarmScreen.setUp;
import static com.example.happyfarm.LoginScreen.USERID;

public class EnterGameActivity extends AppCompatActivity {

    FirebaseFirestore db;
    TextView tv1, tv2;
    String flag;
    int process;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        db = FirebaseFirestore.getInstance();

        setContentView(R.layout.activity_enter_game);
        tv1 = findViewById(R.id.datn);
        tv2 = findViewById(R.id.txt);

        Intent intent = getIntent();
        flag = intent.getStringExtra("flag");

        if (flag.equals("Reg")) pushData();
        if (flag.equals("Login")) getData();
    }



    public void pushData(){
        Thread t1, t2, t3, t5;

        process=0;
        //đưa dữ liệu nông trại lên firestore
        t1 = new Thread(){
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                db = FirebaseFirestore.getInstance();
                ThongTinTaiKhoan accInfor = new ThongTinTaiKhoan();
                accInfor.Create();
                db.collection("ThongTinTaiKhoan").document(USERID)
                        .collection("ThongTinNongTrai").document("ThongTinNongTrai")
                        .set(accInfor, SetOptions.merge())
                        .addOnSuccessListener(unused -> {
                            process+=13;
                            tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                            if (process==100) getData();
                        });
            }
        };
        t1.start();

        //đưa dữ liệu ruộng nông sản lên firestore
        t2 = new Thread(){
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                db = FirebaseFirestore.getInstance();

                RuongNongSan ns_lua = new RuongNongSan(1,1,5,10);
                db.collection("ThongTinTaiKhoan").document(USERID)
                        .collection("RuongNongSan").document(String.valueOf(ns_lua.getNongSanID()))
                        .set(ns_lua, SetOptions.merge())
                        .addOnSuccessListener(unused -> {
                            process+=7;
                            tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                            if (process==100) getData();
                        });

                RuongNongSan ns_cachua = new RuongNongSan(2,1,7,11);
                db.collection("ThongTinTaiKhoan").document(USERID)
                        .collection("RuongNongSan").document(String.valueOf(ns_cachua.getNongSanID()))
                        .set(ns_cachua, SetOptions.merge())
                        .addOnSuccessListener(unused -> {
                            process+=7;
                            tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                            if (process==100) getData();
                        });

                RuongNongSan ns_carot = new RuongNongSan(3,1,9,12);
                db.collection("ThongTinTaiKhoan").document(USERID)
                        .collection("RuongNongSan").document(String.valueOf(ns_carot.getNongSanID()))
                        .set(ns_carot, SetOptions.merge())
                        .addOnSuccessListener(unused -> {
                            process+=7;
                            tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                            if (process==100) getData();
                        });
            }
        };
        t2.start();

        //đưa dữ liệu ô đất lên firestore       12 +24
        t3 = new Thread(){
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                db = FirebaseFirestore.getInstance();
                for (int i=1; i<5;i++) {
                    ODat lua = new ODat();
                    lua.Create(10+i);
                    if (i==1) lua.setMoKhoa(true);
                    db.collection("ThongTinTaiKhoan").document(USERID)
                            .collection("ODat").document(String.valueOf(lua.getoDatID()))
                            .set(lua, SetOptions.merge())
                            .addOnSuccessListener(unused -> {
                                process+=3;
                                tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                                if (process==100) getData();
                            });

                    ODat cachua = new ODat();
                    cachua.Create(20+i);
                    db.collection("ThongTinTaiKhoan").document(USERID)
                            .collection("ODat").document(String.valueOf(cachua.getoDatID()))
                            .set(cachua, SetOptions.merge())
                            .addOnSuccessListener(unused -> {
                                process+=3;
                                tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                                if (process==100) getData();
                            });

                    ODat carot = new ODat();
                    carot.Create(30+i);
                    db.collection("ThongTinTaiKhoan").document(USERID)
                            .collection("ODat").document(String.valueOf(carot.getoDatID()))
                            .set(carot, SetOptions.merge())
                            .addOnSuccessListener(unused -> {
                                process+=3;
                                tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                                if (process==100) getData();
                            });
                }
            }
        };
        t3.start();

        //đưa dữ liệu shop hạt giống lên firestore      30
        t5 = new Thread(){
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                db = FirebaseFirestore.getInstance();
                for (int sp=1; sp<6; sp++) {
                    SanPham sp_lua = new SanPham(10 + sp, "Hạt lúa giống cấp " + sp, "img_lua", "Nâng sản lượng thu hoạch lúa từ " + 10 * (sp - 1) + " lên " + 10 * sp, 20 * sp, false);
                    if (sp!=2) sp_lua.setTrangThaiSanPham(true);
                    String sp_id = String.valueOf(sp_lua.getSanPhamID());
                    db.collection("ThongTinTaiKhoan").document(USERID)
                            .collection("Shop").document(sp_id)
                            .set(sp_lua, SetOptions.merge())
                            .addOnSuccessListener(unused -> {
                                process+=2;
                                tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                                if (process==100) getData();
                            });

                    SanPham sp_cachua = new SanPham(20 + sp, "Hạt cà chua giống cấp " + sp, "img_cachua", "Nâng sản lượng thu hoạch cà chua từ " + 11 * (sp - 1) + " lên " + 11 * sp, 25 * sp, false);
                    if (sp!=2) sp_cachua.setTrangThaiSanPham(true);
                    sp_id = String.valueOf(sp_cachua.getSanPhamID());
                    db.collection("ThongTinTaiKhoan").document(USERID)
                            .collection("Shop").document(sp_id)
                            .set(sp_cachua, SetOptions.merge())
                            .addOnSuccessListener(unused -> {
                                process+=2;
                                tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                                if (process==100) getData();
                            });

                    SanPham sp_carot = new SanPham(30 + sp, "Hạt cà rốt giống cấp " + sp, "img_carot", "Nâng sản lượng thu hoạch cà rốt từ " + 12 * (sp - 1) + " lên " + 12 * sp, 20 * sp, false);
                    sp_id = String.valueOf(sp_carot.getSanPhamID());
                    if (sp!=2) sp_carot.setTrangThaiSanPham(true);
                    db.collection("ThongTinTaiKhoan").document(USERID)
                            .collection("Shop").document(sp_id)
                            .set(sp_carot, SetOptions.merge())
                            .addOnSuccessListener(unused -> {
                                process+=2;
                                tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                                if (process==100) getData();
                            });
                }
            }
        };
        t5.start();
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    public void getData(){
        tv2.setText(String.format("Đang tải dữ liệu nông trại... %d", process));
        process=0;

        db = FirebaseFirestore.getInstance();
        db.collection("ThongTinTaiKhoan").document(USERID)
                .collection("ThongTinNongTrai").document("ThongTinNongTrai")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    ThongTinTaiKhoan accInfor = documentSnapshot.toObject(ThongTinTaiKhoan.class);
                    assert accInfor != null;
                    setUp(accInfor.getTongTienNongTrai(), accInfor.getExpLevel(), accInfor.getGiaTriTheLuc(), accInfor.getTongSoLuongLua(), accInfor.getTongSoluongCachua(), accInfor.getTongSoLuongCaRot());
                    startActivity(new Intent(EnterGameActivity.this,HappyFarmScreen.class));
                })
                .addOnFailureListener(e -> {
                    process=-100;
                    tv2.setText("Tải dữ liệu nông trại thất bại.");
                });
    }
}