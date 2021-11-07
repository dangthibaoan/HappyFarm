package com.example.happyfarm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happyfarm.Model.ODat;
import com.example.happyfarm.Model.RuongNongSan;
import com.example.happyfarm.Model.ThongTinTaiKhoan;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static com.example.happyfarm.EnterGameActivity.O_DAT_UNLOCKED;
import static com.example.happyfarm.EnterGameActivity.setUp;
import static com.example.happyfarm.LoginScreen.CACHUA;
import static com.example.happyfarm.LoginScreen.CAROT;
import static com.example.happyfarm.LoginScreen.FARMCOIN;
import static com.example.happyfarm.LoginScreen.FARMEXP;
import static com.example.happyfarm.LoginScreen.FARMLEVEL;
import static com.example.happyfarm.LoginScreen.LUA;
import static com.example.happyfarm.LoginScreen.STAMINA;
import static com.example.happyfarm.LoginScreen.USERID;
import static java.lang.Math.pow;

public class HappyFarmScreen extends AppCompatActivity {

//    RuongNongSan ruongNongSan;

    ImageView img_bg, imgLogout, imgCoin, imgIcLua, imgIcCachua, imgIcCarot, imgTimeSkip, imgLua, imgCachua, imgCarot, imgDat1, imgDat2, imgDat3, imgDat4, imgPhanbon, imgReact, imgNuoc, imgShop, imgDonhang;
    TextView txtUsn, txtLvl, txtFcoin, txtLua, txtCachua, txtCarot, txtStaVal;

    int oDat1, oDat2, oDat3, oDat4;
    int react, ruongNS, oDatID, viTriODat, process = -1;
    boolean tuoiNuoc, bonPhan;      //true - đã tưới, đã bón; false - chưa tưới, chưa bón

    int soNgayThuHoach, sanLuongThuHoach, levelHatGiong, tienHatGiong;

    ThongTinTaiKhoan thongTinTaiKhoan;

    FirebaseFirestore db;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_happy_farm_screen);

        db = FirebaseFirestore.getInstance();

        txtUsn = findViewById(R.id.txtUserName);
        txtLvl = findViewById(R.id.txtLvl);
        txtFcoin = findViewById(R.id.txtCoin);
        txtLua = findViewById(R.id.txtSolgLua);
        txtCachua = findViewById(R.id.txtSolgCachua);
        txtCarot = findViewById(R.id.txtSolgCarot);
        txtStaVal = findViewById(R.id.txtStaminaValue);

        img_bg = findViewById(R.id.img_bg);
        img_bg.setImageResource(R.drawable.bg_03);

        imgLogout = findViewById(R.id.imgLogout);
        imgLogout.setImageResource(R.drawable.ic_logout);

        imgCoin = findViewById(R.id.imgMoney);
        imgCoin.setImageResource(R.drawable.farmcoin);

        imgIcLua = findViewById(R.id.imgiconLua);
        imgIcLua.setImageResource(R.drawable.ic_lua);

        imgIcCachua = findViewById(R.id.imgiconCachua);
        imgIcCachua.setImageResource(R.drawable.ic_cachua);

        imgIcCarot = findViewById(R.id.imgiconCarot);
        imgIcCarot.setImageResource(R.drawable.ic_carot);

        imgTimeSkip = findViewById(R.id.imgTimeSkip);
        imgTimeSkip.setImageResource(R.drawable.timeskip);

        imgLua = findViewById(R.id.imglua);
        imgCachua = findViewById(R.id.imgcachua);
        imgCarot = findViewById(R.id.imgcarot);

        imgDat1 = findViewById(R.id.imgDat1);
        imgDat2 = findViewById(R.id.imgDat2);
        imgDat3 = findViewById(R.id.imgDat3);
        imgDat4 = findViewById(R.id.imgDat4);

        imgReact = findViewById(R.id.imgReact);
        imgNuoc = findViewById(R.id.imgTuoinc);
        imgPhanbon = findViewById(R.id.imgBonphan);

        imgDonhang = findViewById(R.id.imgOrder);
        imgDonhang.setImageResource(R.drawable.icon_order);

        imgShop = findViewById(R.id.imgShop);
        imgShop.setImageResource(R.drawable.icon_shop);

        //=====================

        //lấy dữ liệu thông tin nông trại
        Thread thread = new Thread(){
            @Override
            public void run() {
                //lấy dữ liệu thông tin nông trại
                db.collection("ThongTinNongTrai").document(USERID)
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            ThongTinTaiKhoan accInfor = documentSnapshot.toObject(ThongTinTaiKhoan.class);
                            assert accInfor != null;
                            setUp(accInfor.getTongTienNongTrai(), accInfor.getExpLevel(), accInfor.getGiaTriTheLuc(), accInfor.getTongSoLuongLua(), accInfor.getTongSoluongCachua(), accInfor.getTongSoLuongCaRot());
                            loadData();
                        });

            }
        };
        thread.start();
        db.collection("ThongTinNongTrai").document(USERID)
                .addSnapshotListener((value, error) -> {
                    ThongTinTaiKhoan accInfor = Objects.requireNonNull(value).toObject(ThongTinTaiKhoan.class);
                    setUp(Objects.requireNonNull(accInfor).getTongTienNongTrai(), accInfor.getExpLevel(), accInfor.getGiaTriTheLuc(), accInfor.getTongSoLuongLua(), accInfor.getTongSoluongCachua(), accInfor.getTongSoLuongCaRot());
                    loadData();
                });


        //==========================
        imgLua.setOnClickListener(v -> {
            ruongNS=1;
            loadData();
        });
        imgCachua.setOnClickListener(v -> {
            ruongNS=2;
            loadData();
        });
        imgCarot.setOnClickListener(v -> {
            ruongNS=3;
            loadData();
       });

        imgDat1.setOnClickListener(view -> {
            viTriODat=1;
            loadData();
        });
        imgDat2.setOnClickListener(view -> {
            viTriODat=2;
            loadData();
        });
        imgDat3.setOnClickListener(view -> {
            viTriODat=3;
            loadData();
        });
        imgDat4.setOnClickListener(view -> {
            viTriODat=4;
            loadData();
        });

        imgReact.setOnClickListener(view -> {
            switch (react){
                case 0://mở khóa ô đất
                    moKhoaODat(oDatID);
                    loadData();
                    break;
                case 1://làm đất
                    lamDat(oDatID);
                    loadData();
                    break;
                case 2://gieo hạt
                    gieoHat(oDatID);
                    loadData();
                    break;
                case 3://thu hoạch
                    switch (oDatID/10){
                        case 1:
                            thuHoach(oDatID,"Lúa");
                            break;
                        case 2:
                            thuHoach(oDatID,"Cà chua");
                            break;
                        case 3:
                            thuHoach(oDatID,"Cà rốt");
                            break;
                    }
                    loadData();
                    break;
                default:
                    loadData();
            }
        });

        imgNuoc.setOnClickListener(view -> {
//            if (tuoiNuoc) {
//
//            } else if (!tuoiNuoc) {
//
//            }
            Toast.makeText(getApplicationContext(), "Tính năng này chưa phát triển.", Toast.LENGTH_SHORT).show();
        });

        imgPhanbon.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), "Tính năng này chưa phát triển.", Toast.LENGTH_SHORT).show();
        });

        imgDonhang.setOnClickListener(v -> {
            //startActivity(new Intent(HappyFarmScreen.this,DonHangScreen.class));
            Toast.makeText(getApplicationContext(), "Tính năng này chưa phát triển.", Toast.LENGTH_SHORT).show();
        });

        imgShop.setOnClickListener(view -> startActivity(new Intent(HappyFarmScreen.this,ShopScreen.class)));

        imgLogout.setOnClickListener(view -> {

        });

    }

    @SuppressLint("DefaultLocale")
    public void loadData(){
        txtUsn.setText(String.format("%s", USERID));
        int level1 = (int) ((int) 100 * (pow(2, FARMLEVEL + 1) - 1));
        txtLvl.setText(String.format("Level %d (%d/%d)", FARMLEVEL, FARMEXP, level1));
        txtFcoin.setText(String.format("%d", FARMCOIN));
        txtLua.setText(String.format("%d", LUA));
        txtCachua.setText(String.format("%d", CACHUA));
        txtCarot.setText(String.format("%d", CAROT));
        txtStaVal.setText(String.format("%d/150", STAMINA));

        switch (react){
            case 0:
                imgReact.setImageResource(R.drawable.farmcoin);
                break;
            case 1:
                imgReact.setImageResource(R.drawable.react_lamdat);
                break;
            case 2:
                imgReact.setImageResource(R.drawable.react_gieohat);
                break;
            case 3:
                imgReact.setImageResource(R.drawable.react_thuhoach);
                break;
            default:
                imgReact.setImageResource(0);
        }
        if (tuoiNuoc) {
            imgNuoc.setImageResource(0);
            imgNuoc.setEnabled(false);
        }
        else {
            imgNuoc.setImageResource(R.drawable.react_tuoinc);
            imgNuoc.setEnabled(true);
        }

        if (bonPhan) {
            imgPhanbon.setImageResource(0);
            imgPhanbon.setEnabled(false);
        }
        else {
            imgPhanbon.setImageResource(R.drawable.react_bonphan);
            imgPhanbon.setEnabled(true);
        }

        switch (ruongNS){
            case 1:
                imgLua.setImageResource(R.drawable.ic_lua);
                imgCachua.setImageResource(R.drawable.ruongcachua);
                imgCarot.setImageResource(R.drawable.ruongcarot);

                imgDat1.setImageResource(0);
                imgDat2.setImageResource(0);
                imgDat3.setImageResource(0);
                imgDat4.setImageResource(0);

                chonRuongNongSan(1);
                break;
            case 2:
                imgLua.setImageResource(R.drawable.ruonglua);
                imgCachua.setImageResource(R.drawable.ic_cachua);
                imgCarot.setImageResource(R.drawable.ruongcarot);

                imgDat1.setImageResource(0);
                imgDat2.setImageResource(0);
                imgDat3.setImageResource(0);
                imgDat4.setImageResource(0);

                chonRuongNongSan(2);
                break;
            case 3:
                imgLua.setImageResource(R.drawable.ruonglua);
                imgCachua.setImageResource(R.drawable.ruongcachua);
                imgCarot.setImageResource(R.drawable.ic_carot);

                imgDat1.setImageResource(0);
                imgDat2.setImageResource(0);
                imgDat3.setImageResource(0);
                imgDat4.setImageResource(0);

                chonRuongNongSan(3);
                break;
            default:
                imgLua.setImageResource(R.drawable.ruonglua);
                imgCachua.setImageResource(R.drawable.ruongcachua);
                imgCarot.setImageResource(R.drawable.ruongcarot);

                imgDat1.setImageResource(0);
                imgDat2.setImageResource(0);
                imgDat3.setImageResource(0);
                imgDat4.setImageResource(0);
        }

        switch (viTriODat){
            case 1:
                imgDat1.setBackgroundResource(R.drawable.bg_03);
                imgDat2.setBackgroundResource(0);
                imgDat3.setBackgroundResource(0);
                imgDat4.setBackgroundResource(0);
                chonODat(oDat1);
                break;
            case 2:
                imgDat1.setBackgroundResource(0);
                imgDat2.setBackgroundResource(R.drawable.bg_03);
                imgDat3.setBackgroundResource(0);
                imgDat4.setBackgroundResource(0);
                chonODat(oDat2);
                break;
            case 3:
                imgDat1.setBackgroundResource(0);
                imgDat2.setBackgroundResource(0);
                imgDat3.setBackgroundResource(R.drawable.bg_03);
                imgDat4.setBackgroundResource(0);
                chonODat(oDat3);
                break;
            case 4:
                imgDat1.setBackgroundResource(0);
                imgDat2.setBackgroundResource(0);
                imgDat3.setBackgroundResource(0);
                imgDat4.setBackgroundResource(R.drawable.bg_03);
                chonODat(oDat4);
                break;
            default:
                imgDat1.setBackgroundResource(0);
                imgDat2.setBackgroundResource(0);
                imgDat3.setBackgroundResource(0);
                imgDat4.setBackgroundResource(0);
        }
    }

    public void chonRuongNongSan(int ruongNSID){
        db = FirebaseFirestore.getInstance();
        db.collection("RuongNongSan").document(USERID)
                .collection("NongSanID").document(String.valueOf(ruongNSID))
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    RuongNongSan ruongNongSan = documentSnapshot.toObject(RuongNongSan.class);
                    assert ruongNongSan != null;
                    levelHatGiong = ruongNongSan.getLevelHatGiong();
                    soNgayThuHoach= ruongNongSan.getSoNgayThuHoach();
                    sanLuongThuHoach = ruongNongSan.getSanLuongThuHoach();
                });
        for (int i=1; i<=4; i++)
            db.collection("ODat").document(USERID)
                    .collection("ODatID").document(String.valueOf(10*ruongNSID+i))
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        ODat oDat = documentSnapshot.toObject(ODat.class);
                        assert oDat != null;
                        switch (oDat.getoDatID() % 10) {
                            case 1:
                                oDat1 = oDat.getoDatID();
                                showODat(oDat, imgDat1);
                                break;
                            case 2:
                                oDat2 = oDat.getoDatID();
                                showODat(oDat, imgDat2);
                                break;
                            case 3:
                                oDat3 = oDat.getoDatID();
                                showODat(oDat, imgDat3);
                                break;
                            case 4:
                                oDat4 = oDat.getoDatID();
                                showODat(oDat, imgDat4);
                                break;
                        }
                    });
    }

    private void showODat(ODat oDat, ImageView img){
        if (!oDat.isMoKhoa()) img.setImageResource(R.drawable.dat_locked);
        else if (!oDat.isLamDat()) img.setImageResource(R.drawable.dat_unlocked);
        else if (!oDat.isGieoHat()) img.setImageResource(R.drawable.dat_ok);
        else {
            switch (oDat.getoDatID()/10){
                case 1:
                    if (oDat.getSoNgayThuHoach() <= 5) img.setImageResource(R.drawable.ns_lua01);
                    if (oDat.getSoNgayThuHoach() <= 3) img.setImageResource(R.drawable.ns_lua02);
                    if (oDat.getSoNgayThuHoach() <= 1) img.setImageResource(R.drawable.ns_lua03);
                    break;
                case 2:
                    if (oDat.getSoNgayThuHoach() <= 7) img.setImageResource(R.drawable.ns_lua01);
                    if (oDat.getSoNgayThuHoach() <= 4) img.setImageResource(R.drawable.ns_lua02);
                    if (oDat.getSoNgayThuHoach() <= 1) img.setImageResource(R.drawable.ns_lua03);
                    break;
                case 3:
                    if (oDat.getSoNgayThuHoach() <= 9) img.setImageResource(R.drawable.ns_lua01);
                    if (oDat.getSoNgayThuHoach() <= 5) img.setImageResource(R.drawable.ns_lua02);
                    if (oDat.getSoNgayThuHoach() <= 1) img.setImageResource(R.drawable.ns_lua03);
                    break;
            }
        }
    }

    public void chonODat(int oDatId){
        oDatID = oDatId;
        db = FirebaseFirestore.getInstance();
        db.collection("ODat").document(USERID)
                .collection("ODatID").document(String.valueOf(oDatId))
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    ODat oDat = documentSnapshot.toObject(ODat.class);
                    assert oDat != null;
                    if (!oDat.isMoKhoa()) {
                        react=0;
                        imgReact.setImageResource(R.drawable.farmcoin);
                        Toast.makeText(getApplicationContext(), "Tạm thời chưa thể mở khóa ô đất này.", Toast.LENGTH_SHORT).show();
                    }
                    else if (!oDat.isLamDat()) {
                        react = 1;
                        imgReact.setImageResource(R.drawable.react_lamdat);
                    }
                    else if (!oDat.isGieoHat()) {
                        react = 2;
                        imgReact.setImageResource(R.drawable.react_gieohat);
                    }
                    else {
                        react = 3;
                        imgReact.setImageResource(R.drawable.react_thuhoach);
                        imgPhanbon.setEnabled(!oDat.isBonPhan());
                        bonPhan = oDat.isBonPhan();
                        imgNuoc.setEnabled(!oDat.isTuoiNuoc());
                        tuoiNuoc = oDat.isTuoiNuoc();
                        soNgayThuHoach = oDat.getSoNgayThuHoach();
                        sanLuongThuHoach = oDat.getSanLuongThuHoach();
                    }
                });
    }

    @SuppressLint("DefaultLocale")
    public void  moKhoaODat(int oDatID){
        process=0;
        if (FARMCOIN >= (100*O_DAT_UNLOCKED))
        {
            //trừ tiền
            Thread truTien = new Thread(){
                @SuppressLint("DefaultLocale")
                @Override
                public void run() {
                    db = FirebaseFirestore.getInstance();
                    db.collection("ThongTinNongTrai").document(USERID)
                            .update("tongTienNongTrai",FARMCOIN - (100 * O_DAT_UNLOCKED))
                            .addOnSuccessListener(aVoid -> {
                                process+=50;
                                Toast.makeText(getApplicationContext(), String.format("Đã trừ %d tiền nông trại.", O_DAT_UNLOCKED * 100), Toast.LENGTH_LONG).show();
                                if (process==100) Toast.makeText(getApplicationContext(),"Mở khóa ô đất thành công", Toast.LENGTH_LONG).show();
                                Log.d("TAG", "onSuccess: Update success");
                            })
                            .addOnFailureListener(e -> {
                                process = -100;
                                Toast.makeText(getApplicationContext(),"Lỗi", Toast.LENGTH_LONG).show();
                            });
                }
            };
            truTien.start();

            //cập nhật trạng thái ô đất
            Thread moKhoa = new Thread(){
                @Override
                public void run() {
                    db = FirebaseFirestore.getInstance();
                    db.collection("ODat").document(USERID)
                            .collection("ODatID").document(String.valueOf(oDatID))
                            .update("moKhoa",true)
                            .addOnSuccessListener(aVoid -> {
                                process+=50;
                                if (process==100) Toast.makeText(getApplicationContext(),"Mở khóa ô đất thành công", Toast.LENGTH_LONG).show();
                                Log.d("TAG", "onSuccess: Update success");
                            })
                            .addOnFailureListener(e -> {
                                process = -100;
                                Toast.makeText(getApplicationContext(),"Lỗi.", Toast.LENGTH_LONG).show();
                            });

                }
            };
            moKhoa.start();
        }
        else {
            Toast.makeText(getApplicationContext(), String.format("Mở khóa ô đất này cần %d tiền nông trại.", O_DAT_UNLOCKED * 100), Toast.LENGTH_LONG).show();
        }
    }

    public void lamDat(int oDatID){
        if (STAMINA>0){
            //cập nhật trạng thái ô đất
            Thread lamDat = new Thread(){
                @Override
                public void run() {
                    db = FirebaseFirestore.getInstance();
                    db.collection("ODat").document(USERID)
                            .collection("ODatID").document(String.valueOf(oDatID))
                            .update("lamDat",true)
                            .addOnSuccessListener(aVoid -> {
                                Log.d("TAG", "onSuccess: Update success");
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được trạng thái ô đất.", Toast.LENGTH_LONG).show();
                                Log.d("TAG", "onFailure: Update fail");
                            });
                }
            };
            lamDat.start();

            //trừ thể lực
            Thread truTheLuc = new Thread(){
                @SuppressLint("DefaultLocale")
                @Override
                public void run() {
                    db = FirebaseFirestore.getInstance();
                    db.collection("ThongTinNongTrai").document(USERID)
                            .update("giaTriTheLuc",STAMINA-=10)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getApplicationContext(), "Thể lực -10.", Toast.LENGTH_LONG).show();
                                if (STAMINA<=0) {
                                    Toast.makeText(getApplicationContext(),"Kiệt sức. Bạn cần nghỉ ngơi.", Toast.LENGTH_LONG).show();
                                    //chạy chức năng sang ngày mới
                                }
                                Log.d("TAG", "onSuccess: Update success");
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được thông tin nông trại", Toast.LENGTH_LONG).show();
                            });
                }
            };
            truTheLuc.start();
        }
    }

    public void gieoHat(int oDatID){
        if (STAMINA>0){
            switch (oDatID/10){
                case 1:
                    tienHatGiong = 5*levelHatGiong;
                    break;
                case 2:
                    tienHatGiong = 6*levelHatGiong;
                    break;
                case 3:
                    tienHatGiong = 7*levelHatGiong;
                    break;
                default:
                    tienHatGiong = 0;
                    throw new IllegalStateException("Unexpected value: " + oDatID / 10);
            }
            if (FARMCOIN>=levelHatGiong && tienHatGiong!=0){
                //cập nhật trạng thái ô đất
                Thread gieoHat = new Thread(){
                    @Override
                    public void run() {
                        db = FirebaseFirestore.getInstance();
                        db.collection("ODat").document(USERID)
                                .collection("ODatID").document(String.valueOf(oDatID))
                                .update("gieoHat",true)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(getApplicationContext(), "Thể lực -10.", Toast.LENGTH_LONG).show();
                                    if (STAMINA<=0) {
                                        Toast.makeText(getApplicationContext(),"Kiệt sức. Bạn cần nghỉ ngơi.", Toast.LENGTH_LONG).show();
                                        //chạy chức năng sang ngày mới
                                    }
                                    Log.d("TAG", "onSuccess: Update success");
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được trạng thái ô đất.", Toast.LENGTH_LONG).show();
                                });
                    }
                };
                gieoHat.start();

                //trừ thể lực, tiền nông trại
                Thread truTheLuc = new Thread(){
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void run() {
                        db = FirebaseFirestore.getInstance();
                        db.collection("ThongTinNongTrai").document(USERID)
                                .update("giaTriTheLuc",STAMINA-10,
                                        "tongTienNongTrai",FARMCOIN - (tienHatGiong))
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(getApplicationContext(), String.format("Thể lực -10\nFarmcoin - %d", tienHatGiong), Toast.LENGTH_LONG).show();
                                    Log.d("TAG", "onSuccess: Update success");
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được thông tin nông trại", Toast.LENGTH_SHORT).show();
                                });
                    }
                };
                truTheLuc.start();
            }
        }
    }

    public void thuHoach(int oDatID, String nongSan){
        if (STAMINA>0){
            //cập nhật tổng số lượng nông sản
            switch (oDatID/10){
                case 1:
                    LUA += sanLuongThuHoach;
                    break;
                case 2:
                    CACHUA += sanLuongThuHoach;
                    break;
                case 3:
                    CAROT += sanLuongThuHoach;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + oDatID / 10);
            }
            FARMEXP += 5*sanLuongThuHoach;
            STAMINA -=10;

            thongTinTaiKhoan.GetData();

            //trừ thể lực
            Thread truTheLuc = new Thread(){
                @SuppressLint("DefaultLocale")
                @Override
                public void run() {
                    db = FirebaseFirestore.getInstance();
                    db.collection("ThongTinNongTrai").document(USERID)
                            .update("tongTienNongTrai", thongTinTaiKhoan.getTongTienNongTrai(),
                                    "expLevel",thongTinTaiKhoan.getExpLevel(),
                                    "giaTriTheLuc", thongTinTaiKhoan.getGiaTriTheLuc(),
                                    "tongSoLuongLua", thongTinTaiKhoan.getTongSoLuongLua(),
                                    "tongSoLuongCachua",thongTinTaiKhoan.getTongSoluongCachua(),
                                    "tongSoLuongCaRot",thongTinTaiKhoan.getTongSoLuongCaRot())
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getApplicationContext(),
                                        String.format("Thể lực -10\nFarmexp + %d\n%S + %d",5*sanLuongThuHoach, nongSan, sanLuongThuHoach),
                                        Toast.LENGTH_LONG).show();
                                if (STAMINA<=0) {
                                    Toast.makeText(getApplicationContext(),"Kiệt sức. Bạn cần nghỉ ngơi.", Toast.LENGTH_LONG).show();
                                    //chạy chức năng sang ngày mới
                                }
                                Log.d("TAG", "onSuccess: Update success");
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được thông tin nông trại", Toast.LENGTH_LONG).show();
                            });
                }
            };
            truTheLuc.start();

            //reset thông tin ô đất
            Thread thuHoach = new Thread(){
                @Override
                public void run() {
                    db = FirebaseFirestore.getInstance();
                    db.collection("ODat").document(USERID)
                            .collection("ODatID").document(String.valueOf(oDatID))
                            .update("lamDat", false,
                                    "gieoHat",false,
                                    "tuoiNuoc", false,
                                    "bonPhan", false,
                                    "soNgayThuHoach",0,
                                    "sanLuongThuHoach",0)
                            .addOnSuccessListener(aVoid -> {
                                Log.d("TAG", "onSuccess: Update success");
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được trạng thái ô đất.", Toast.LENGTH_LONG).show();
                            });
                }
            };
            thuHoach.start();
        }
    }



//    public void NangCapHatGiong(int ns_id){
//        int level = 0;
//        FirebaseFirestore db;
//        db = FirebaseFirestore.getInstance();
//
//        //lấy dữ liệu ruộng
//        String id = String.valueOf(ns_id);
//        db.collection("RuongNongSan").document(USERID)
//                .collection("NongSanID").document(id)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//                                ruongNongSan = document.toObject(RuongNongSan.class);
//
//                            } else {
//                                Log.d("TAG", "No such document");
//                            }
//                        } else {
//                            Log.d("TAG", "get failed with ", task.getException());
//                        }
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("TAG", "onFailure: Load data fail" + e);
//                    }
//                });
//        level = ruongNongSan.getLevelHatGiong();
//        level++;
//
//
//    }

}