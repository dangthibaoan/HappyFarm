package com.example.happyfarm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.happyfarm.Model.DonHang;
import com.example.happyfarm.Model.ODat;
import com.example.happyfarm.Model.RuongNongSan;
import com.example.happyfarm.Model.SanPham;
import com.example.happyfarm.Model.ThongTinTaiKhoan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Objects;

import static com.example.happyfarm.LoginScreen.CACHUA;
import static com.example.happyfarm.LoginScreen.CAROT;
import static com.example.happyfarm.LoginScreen.FARMCOIN;
import static com.example.happyfarm.LoginScreen.FARMEXP;
import static com.example.happyfarm.LoginScreen.LUA;
import static com.example.happyfarm.LoginScreen.STAMINA;
import static com.example.happyfarm.LoginScreen.USERID;
import static java.lang.Math.log;

public class EnterGameActivity extends AppCompatActivity {

    public static int O_DAT_UNLOCKED;

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

    public static void setUp(int fCoin, int exp, int sta, int lua, int cachua, int carot){
        FARMCOIN = fCoin;
        FARMEXP = exp;
        double kq = (log((exp / 100) + 1) / log(2));
        LoginScreen.FARMLEVEL = (int) kq;
        STAMINA = sta;
        LUA = lua;
        CACHUA = cachua;
        CAROT = carot;
    }

    public void pushData(){
        Thread t1, t2, t3, t4, t5;

        process=0;
        //đưa dữ liệu nông trại lên firestore
        t1 = new Thread(){
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                db = FirebaseFirestore.getInstance();
                ThongTinTaiKhoan accInfor = new ThongTinTaiKhoan();
                accInfor.setuID(USERID);
                accInfor.Create();
                db.collection("ThongTinNongTrai").document(USERID)
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
                RuongNongSan ns_lua = new RuongNongSan(USERID,1,1,5,10);
//                String ns_lua_id = String.valueOf(ns_lua.getNongSanID());
                db.collection("RuongNongSan").document(USERID)
                        .collection("NongSanID").document(String.valueOf(ns_lua.getNongSanID()))
                        .set(ns_lua, SetOptions.merge())
                        .addOnSuccessListener(unused -> {
                            process+=7;
                            tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                            if (process==100) getData();
                        });

                RuongNongSan ns_cachua = new RuongNongSan(USERID,2,1,7,11);
//                String ns_cachua_id = String.valueOf(ns_lua.getNongSanID());
                db.collection("RuongNongSan").document(USERID)
                        .collection("NongSanID").document(String.valueOf(ns_cachua.getNongSanID()))
                        .set(ns_cachua, SetOptions.merge())
                        .addOnSuccessListener(unused -> {
                            process+=7;
                            tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                            if (process==100) getData();
                        });

                RuongNongSan ns_carot = new RuongNongSan(USERID,3,1,9,12);
//                String ns_carot_id = String.valueOf(ns_lua.getNongSanID());
                db.collection("RuongNongSan").document(USERID)
                        .collection("NongSanID").document(String.valueOf(ns_carot.getNongSanID()))
                        .set(ns_carot, SetOptions.merge())
                        .addOnSuccessListener(unused -> {
                            process+=7;
                            tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                            if (process==100) getData();
                        });
            }
        };
        t2.start();

        //đưa dữ liệu ô đất lên firestore       12
        t3 = new Thread(){
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                db = FirebaseFirestore.getInstance();
                for (int i=1; i<5;i++) {
                    ODat lua = new ODat(USERID, 10 + i, false, false, false, false, false, 0, 0);
                    lua.Create(10+i);
                    if (i==1) lua.setMoKhoa(true);
                    db.collection("ODat").document(USERID)
                            .collection("ODatID").document(String.valueOf(lua.getoDatID()))
                            .set(lua, SetOptions.merge())
                            .addOnSuccessListener(unused -> {
                                process+=1;
                                tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                                if (process==100) getData();
                            });

                    ODat cachua = new ODat(USERID, 20 + i, false, false, false, false, false, 0, 0);
                    cachua.Create(20+i);
                    db.collection("ODat").document(USERID)
                            .collection("ODatID").document(String.valueOf(cachua.getoDatID()))
                            .set(cachua, SetOptions.merge())
                            .addOnSuccessListener(unused -> {
                                process+=1;
                                tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                                if (process==100) getData();
                            });

                    ODat carot = new ODat(USERID, 30 + i, false, false, false, false, false, 0, 0);
                    carot.Create(30+i);
                    db.collection("ODat").document(USERID)
                            .collection("ODatID").document(String.valueOf(carot.getoDatID()))
                            .set(carot, SetOptions.merge())
                            .addOnSuccessListener(unused -> {
                                process+=1;
                                tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                                if (process==100) getData();
                            });
                }
            }
        };
        t3.start();

        //đưa dữ liệu đơn hàng lên firestore        24
        t4 = new Thread(){
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                db = FirebaseFirestore.getInstance();
                for(int ii=0;ii<12;ii++){
                    DonHang donHang = new DonHang();
                    donHang.setUid(USERID);
                    donHang.setDonHangID(ii);
                    donHang.Create();
                    db.collection("DonHang").document(USERID)
                            .collection("DonHangID").document(String.valueOf(donHang.getDonHangID()))
                            .set(donHang, SetOptions.merge())
                            .addOnSuccessListener(unused -> {
                                process+=2;
                                tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                                if (process==100) getData();
                            });
                }
            }
        };
        t4.start();

        //đưa dữ liệu shop hạt giống lên firestore      30
        t5 = new Thread(){
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                db = FirebaseFirestore.getInstance();
                for (int sp=1; sp<6; sp++) {
                    SanPham sp_lua = new SanPham(USERID, 10 + sp, "Hạt lúa giống cấp " + sp, "img_lua", "Nâng sản lượng thu hoạch lúa từ " + 10 * (sp - 1) + " lên " + 10 * sp, 20 * sp, false);
                    String sp_id = String.valueOf(sp_lua.getSanPhamID());
                    db.collection("Shop").document(USERID)
                            .collection("SanPhamID").document(sp_id)
                            .set(sp_lua, SetOptions.merge())
                            .addOnSuccessListener(unused -> {
                                process+=2;
                                tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                                if (process==100) getData();
                            });

                    SanPham sp_cachua = new SanPham(USERID, 20 + sp, "Hạt cà chua giống cấp " + sp, "img_cachua", "Nâng sản lượng thu hoạch cà chua từ " + 11 * (sp - 1) + " lên " + 11 * sp, 25 * sp, false);
                    sp_id = String.valueOf(sp_cachua.getSanPhamID());
                    db.collection("Shop").document(USERID)
                            .collection("SanPhamID").document(sp_id)
                            .set(sp_cachua, SetOptions.merge())
                            .addOnSuccessListener(unused -> {
                                process+=2;
                                tv2.setText(String.format("Đang thiết lập dữ liệu, tiến độ %d (%%)", process));
                                if (process==100) getData();
                            });

                    SanPham sp_carot = new SanPham(USERID, 30 + sp, "Hạt cà rốt giống cấp " + sp, "img_carot", "Nâng sản lượng thu hoạch cà rốt từ " + 12 * (sp - 1) + " lên " + 12 * sp, 20 * sp, false);
                    sp_id = String.valueOf(sp_carot.getSanPhamID());
                    db.collection("Shop").document(USERID)
                            .collection("SanPhamID").document(sp_id)
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
        process=0;
        db = FirebaseFirestore.getInstance();
        db.collection("ThongTinNongTrai").document(USERID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    ThongTinTaiKhoan accInfor = documentSnapshot.toObject(ThongTinTaiKhoan.class);
                    assert accInfor != null;
                    setUp(accInfor.getTongTienNongTrai(), accInfor.getExpLevel(), accInfor.getGiaTriTheLuc(), accInfor.getTongSoLuongLua(), accInfor.getTongSoluongCachua(), accInfor.getTongSoLuongCaRot());
                    process+=50;
                    tv2.setText(String.format("Đang tải dữ liệu nông trại, tiến độ %d (%%)", process));
                    if (process==100) startActivity(new Intent(EnterGameActivity.this,HappyFarmScreen.class));
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        process=-100;
                        tv2.setText("Tải dữ liệu nông trại thất bại.");
                    }
                });
        O_DAT_UNLOCKED=0;
        db.collection("ODat").document(USERID)
                .collection("ODatID").whereEqualTo("moKhoa",true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //noinspection ConstantConditions
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                O_DAT_UNLOCKED++;
                            }
                            process+=50;
                            tv2.setText(String.format("Đang tải dữ liệu nông trại, tiến độ %d (%%)", process));
                            if (process==100) startActivity(new Intent(EnterGameActivity.this,HappyFarmScreen.class));
                        } else {
                            Log.d("TAG", "onComplete: Load data error " + task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        process=-100;
                        tv2.setText("Tải dữ liệu thất bại.");
                    }
                });
    }
}