package com.example.happyfarm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happyfarm.Adapter.DonHangAdapter;
import com.example.happyfarm.Adapter.SPAdapter;
import com.example.happyfarm.Model.DonHang;
import com.example.happyfarm.Model.ODat;
import com.example.happyfarm.Model.RuongNongSan;
import com.example.happyfarm.Model.SanPham;
import com.example.happyfarm.Model.ThongTinTaiKhoan;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.happyfarm.LoginScreen.USERID;
import static java.lang.Math.log;

public class HappyFarmScreen extends AppCompatActivity {
    public static int O_DAT_UNLOCKED;
    public static int FARMCOIN, FARMLEVEL, FARMEXP, STAMINA, LUA, CACHUA, CAROT;

    boolean doubleBackToExitApp = false;

    ImageView img_bg, imgLogout, imgCoin, imgIcLua, imgIcCachua, imgIcCarot, imgTimeSkip, imgLua, imgCachua, imgCarot, imgDat1, imgDat2, imgDat3, imgDat4, imgPhanbon, imgReact, imgNuoc, imgShop, imgDonhang;
    TextView txtUsn, txtLvl, txtFcoin, txtLua, txtCachua, txtCarot, txtStaVal, txt01;

    List<DonHang> donHangList;
    DonHangAdapter donHangAdapter;

    ListView listView;
    ImageView imgGiaoHang, imgBoQua;

    List<SanPham> sanPhamList;
    SPAdapter adapter;

    TextView txtUID, txtCoin;
    ListView listViewShop;
    ImageView imgCoinShop;
    Button btnMua;

    int vitri = -1;
    int donHangID, nongSanID, soLuongMua, tienHang;

    int idSP, donGiaSP;
    String tenSP;
    Boolean status;

    int oDat1, oDat2, oDat3, oDat4;
    int react, ruongNS, oDatID, viTriODat, process = -1;
    boolean tuoiNuoc, bonPhan = false;      //true - đã tưới, đã bón; false - chưa tưới, chưa bón

    int soNgayThuHoachBanDau, soNgayThuHoach, sanLuongThuHoachBanDau, sanLuongThuHoach, levelHatGiong, tienHatGiong;

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
        txt01 = findViewById(R.id.txt01);

        img_bg = findViewById(R.id.img_bg);
        img_bg.setImageResource(R.drawable.bg_03);

        imgLogout = findViewById(R.id.imgLogout);
        imgLogout.setImageResource(R.drawable.bg_01);

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
        imgNuoc.setImageResource(R.drawable.react_tuoinc);

        imgPhanbon = findViewById(R.id.imgBonphan);
        imgPhanbon.setImageResource(R.drawable.react_bonphan);

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
                db.collection("ThongTinTaiKhoan").document(USERID)
                        .collection("ThongTinNongTrai").document("ThongTinNongTrai")
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
        db.collection("ThongTinTaiKhoan").document(USERID)
                .collection("ThongTinNongTrai").document("ThongTinNongTrai")
                .addSnapshotListener((value, error) -> {
                    ThongTinTaiKhoan accInfor = Objects.requireNonNull(value).toObject(ThongTinTaiKhoan.class);
                    assert accInfor != null;
                    setUp(accInfor.getTongTienNongTrai(), accInfor.getExpLevel(), accInfor.getGiaTriTheLuc(), accInfor.getTongSoLuongLua(), accInfor.getTongSoluongCachua(), accInfor.getTongSoLuongCaRot());
                    loadData();
                });


        //==========================
        imgLua.setOnClickListener(v -> {
            react = oDatID = viTriODat = -1;
            tuoiNuoc = bonPhan = false;

            ruongNS=1;
            loadData();
        });
        imgCachua.setOnClickListener(v -> {
            react = oDatID = viTriODat = -1;
            tuoiNuoc = bonPhan = false;

            if (FARMLEVEL<3) Toast.makeText(getApplicationContext(), "Nông trại của bạn chưa đạt level 3.", Toast.LENGTH_SHORT).show();
            else {
                ruongNS=2;
                loadData();
            }

        });
        imgCarot.setOnClickListener(v -> {
            react = oDatID = viTriODat = -1;
            tuoiNuoc = bonPhan = false;

            if (FARMLEVEL<10) Toast.makeText(getApplicationContext(), "Nông trại của bạn chưa đạt level 10.", Toast.LENGTH_SHORT).show();
            else {
                ruongNS=3;
                loadData();
            }
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
                    getODatUnlocked(oDatID);
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
                case 3://chờ thu hoạch
                    Toast.makeText(getApplicationContext(), "Chưa thể thu hoạch.", Toast.LENGTH_SHORT).show();
                    break;
                case 4://thu hoạch
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
            if (react==3) tuoiNuoc(oDatID);
            else Toast.makeText(getApplicationContext(), "Tưới nước mỗi ngày sẽ thu hoạch được nhiều hơn.", Toast.LENGTH_SHORT).show();
        });

        imgPhanbon.setOnClickListener(view -> {
            if (react==3) bonPhan(oDatID);
            else Toast.makeText(getApplicationContext(), "Bón phân mỗi ngày sẽ rút ngắn thời gian thu hoạch.", Toast.LENGTH_SHORT).show();
        });

        imgDonhang.setOnClickListener(v -> {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") View view1 = inflater.inflate(R.layout.dialog_don_hang_screen, null);
            //.....................

            listView = view1.findViewById(R.id.lvDonHang);
            imgGiaoHang = view1.findViewById(R.id.imgGiaohang);
            imgBoQua = view1.findViewById(R.id.imgBoqua);

            donHangList = new ArrayList<>();
            for(int ii=0;ii<12;ii++){
                DonHang donHang = new DonHang();
                donHang.setDonHangID(ii);
                donHang.Create();
                donHangList.add(donHang);
            }
            donHangAdapter = new DonHangAdapter(getApplicationContext(),donHangList);
            listView.setAdapter(donHangAdapter);

            listView.setOnItemClickListener((parent, view, position, id) -> {
                DonHang donHang = (DonHang) parent.getItemAtPosition(position);
                vitri = position;
                donHangID = donHang.getDonHangID();
                nongSanID = donHang.getNongSanID();
                soLuongMua = donHang.getSoLuongMua();
                tienHang = donHang.getTienHang();
            });

            imgGiaoHang.setOnClickListener(v1 -> {
                if (vitri == -1)
                    Toast.makeText(HappyFarmScreen.this.getApplicationContext(), "Chưa chọn đơn hàng.", Toast.LENGTH_SHORT).show();
                else
                    HappyFarmScreen.this.giaoHang(vitri);
            });

            imgBoQua.setOnClickListener(v2 -> {
                if (vitri==-1)
                    Toast.makeText(getApplicationContext(),"Chưa chọn đơn hàng.", Toast.LENGTH_SHORT).show();
                else
                    boQua(vitri);
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(HappyFarmScreen.this);
            builder.setTitle("Đơn đặt hàng")
                    .setView(view1)
                    .setNegativeButton("Đóng", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        imgShop.setOnClickListener(view -> {
            if (FARMLEVEL<3){
                Toast.makeText(getApplicationContext(), "Nông trại level 3 sẽ mở shop hạt giống.", Toast.LENGTH_SHORT).show();
            } else {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View view1 = inflater.inflate(R.layout.dialog_shop_screen, null);
                //.....................

                txtUID = view1.findViewById(R.id.txtUID);
                txtCoin = view1.findViewById(R.id.txtCoin);
                listViewShop = view1.findViewById(R.id.lvSanpham);
                imgCoinShop = view1.findViewById(R.id.imgCoin);
                btnMua = view1.findViewById(R.id.btnMua);

                txtUID.setText(USERID);
                txtCoin.setText(String.valueOf(FARMCOIN));
                imgCoinShop.setImageResource(R.drawable.farmcoin);

                btnMua.setEnabled(false);

                db = FirebaseFirestore.getInstance();
                db.collection("ThongTinTaiKhoan").document(USERID)
                        .collection("Shop").whereEqualTo("trangThaiSanPham",false)
                        .get()
                        .addOnCompleteListener(task -> {
                            sanPhamList = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot snapshot : Objects.requireNonNull(task.getResult())) {
                                    SanPham sanPham = snapshot.toObject(SanPham.class);
                                    sanPhamList.add(sanPham);
                                }
                                adapter = new SPAdapter(getApplicationContext(),sanPhamList);
                                listViewShop.setAdapter(adapter);
                            } else {
                                Toast.makeText(getApplicationContext(), "Lỗi!", Toast.LENGTH_SHORT).show();
                                Log.d("Tag", "onComplete: Load data error " + task.getException());
                            }
                        });
                db.collection("ThongTinTaiKhoan").document(USERID)
                        .collection("Shop").whereEqualTo("trangThaiSanPham",false)
                        .addSnapshotListener((value, error) -> {
                            sanPhamList = new ArrayList<>();
                            assert value != null;
                            for (QueryDocumentSnapshot snapshot : value) {
                                SanPham sanPham = snapshot.toObject(SanPham.class);
                                sanPhamList.add(sanPham);
                            }
                            adapter = new SPAdapter(getApplicationContext(),sanPhamList);
                            listViewShop.setAdapter(adapter);
                        });

                listViewShop.setOnItemClickListener((parent, v, position, id) -> {
                    SanPham sanPham = (SanPham) parent.getItemAtPosition(position);
                    idSP = sanPham.getSanPhamID();
                    tenSP = sanPham.getTenSanPham();
                    donGiaSP = sanPham.getDonGia();
                    status = sanPham.isTrangThaiSanPham();
                    if (status){
                        btnMua.setText(String.format("%d", donGiaSP));
                        btnMua.setEnabled(false);
                    } else {
                        btnMua.setText(String.format("%d", donGiaSP));
                        btnMua.setEnabled(true);
                    }
                });

                btnMua.setOnClickListener(v -> {
                    if (FARMCOIN >= donGiaSP){
                        //trừ tiền
                        Thread truTien = new Thread(){
                            @SuppressLint("DefaultLocale")
                            @Override
                            public void run() {
                                db = FirebaseFirestore.getInstance();
                                db.collection("ThongTinTaiKhoan").document(USERID)
                                        .collection("ThongTinNongTrai").document("ThongTinNongTrai")
                                        .update("tongTienNongTrai",FARMCOIN - donGiaSP)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(getApplicationContext(), String.format("Farmcoin - %d.", donGiaSP), Toast.LENGTH_SHORT).show();
                                            Log.d("TAG", "onSuccess: Update success");
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"Lỗi", Toast.LENGTH_SHORT).show());
                            }
                        };
                        truTien.start();
                        //cập nhật thông tin shop
                        Thread updateShop = new Thread(){
                            @Override
                            public void run() {
                                db = FirebaseFirestore.getInstance();
                                db.collection("ThongTinTaiKhoan").document(USERID)
                                        .collection("Shop").document(String.valueOf(idSP))
                                        .update("trangThaiSanPham", true)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(getApplicationContext(), String.format("Mua %s thành công", tenSP), Toast.LENGTH_SHORT).show();
                                            Log.d("TAG", "onSuccess: Update success");
                                        })
                                        .addOnFailureListener(e -> Log.d("TAG", "onFailure: Update success"));
                                if (idSP%10 < 5)
                                    db.collection("ThongTinTaiKhoan").document(USERID)
                                        .collection("Shop").document(String.valueOf(idSP+1))
                                        .update("trangThaiSanPham", false)
                                        .addOnSuccessListener(aVoid -> Log.d("TAG", "onSuccess: Update success"))
                                        .addOnFailureListener(e -> Log.d("TAG", "onFailure: Update success"));
                            }
                        };
                        updateShop.start();
                        if (idSP/10==1) updateRuongNS(idSP / 10, idSP % 10, 10 * (idSP % 10));
                        else if (idSP/10==2) updateRuongNS(idSP / 10, idSP % 10, 11 * (idSP % 10));
                        else if (idSP/10==3) updateRuongNS(idSP / 10, idSP % 10, 12 * (idSP % 10));

                    } else
                        Toast.makeText(getApplicationContext(), "Không đủ tiền nông trại", Toast.LENGTH_SHORT).show();
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(HappyFarmScreen.this);
                builder.setTitle("Shop hạt giống")
                        .setView(view1)
                        .setNegativeButton("Đóng", (dialog, which) -> dialog.dismiss());
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        imgTimeSkip.setOnClickListener(v -> timeSkip());

//        imgLogout.setOnClickListener(view -> {});

    }

    public static void setUp(int fCoin, int exp, int sta, int lua, int cachua, int carot){
        FARMCOIN = fCoin;
        FARMEXP = exp;
        double kq = (log((exp / 100) + 1) / log(2));
        FARMLEVEL = (int) kq;
        STAMINA = sta;
        LUA = lua;
        CACHUA = cachua;
        CAROT = carot;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    public void loadData(){
        img_bg.setBackgroundResource(0);
        img_bg.setImageResource(R.drawable.bg_03);

        txtUsn.setText(String.format("%s", USERID));
        int level1 = (int) ( 100 * (Math.pow(2, FARMLEVEL + 1) - 1));
        txtLvl.setText(String.format("Level %d (%d/%d)", FARMLEVEL, FARMEXP, level1));
        txtFcoin.setText(String.format("%d", FARMCOIN));
        txtLua.setText(String.format("%d", LUA));
        txtCachua.setText(String.format("%d", CACHUA));
        txtCarot.setText(String.format("%d", CAROT));
        txtStaVal.setText(String.format("%d/150", STAMINA));
        txt01.setText("Sang ngày mới");

        switch (react){
            case 0:
                imgReact.setImageResource(R.drawable.farmcoin);
                imgReact.setEnabled(true);
                break;
            case 1:
                imgReact.setImageResource(R.drawable.react_lamdat);
                imgReact.setEnabled(true);
                break;
            case 2:
                imgReact.setImageResource(R.drawable.react_gieohat);
                imgReact.setEnabled(true);
                break;
            case 3:
                imgReact.setImageResource(R.drawable.react_thuhoach);
                imgReact.setEnabled(false);
                break;
            case 4:
                imgReact.setImageResource(R.drawable.react_thuhoach);
                imgReact.setEnabled(true);
                break;
            default:
                imgReact.setImageResource(0);
                imgReact.setEnabled(false);
        }

        switch (ruongNS){
            case 1:
                imgLua.setImageResource(R.drawable.ic_lua);
                imgCachua.setImageResource(R.drawable.ruongcachua);
                imgCarot.setImageResource(R.drawable.ruongcarot);

                chonRuongNongSan(1);
                break;
            case 2:
                imgLua.setImageResource(R.drawable.ruonglua);
                imgCachua.setImageResource(R.drawable.ic_cachua);
                imgCarot.setImageResource(R.drawable.ruongcarot);

                chonRuongNongSan(2);
                break;
            case 3:
                imgLua.setImageResource(R.drawable.ruonglua);
                imgCachua.setImageResource(R.drawable.ruongcachua);
                imgCarot.setImageResource(R.drawable.ic_carot);

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

                imgReact.setImageResource(0);
                imgReact.setEnabled(false);
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

                imgNuoc.setImageResource(R.drawable.react_tuoinc);
                imgPhanbon.setImageResource(R.drawable.react_bonphan);
        }

    }

    public void chonRuongNongSan(int ruongNSID){
        db = FirebaseFirestore.getInstance();
        db.collection("ThongTinTaiKhoan").document(USERID)
                .collection("RuongNongSan").document(String.valueOf(ruongNSID))
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    RuongNongSan ruongNongSan = documentSnapshot.toObject(RuongNongSan.class);
                    assert ruongNongSan != null;
                    levelHatGiong = ruongNongSan.getLevelHatGiong();
                    soNgayThuHoachBanDau = ruongNongSan.getSoNgayThuHoach();
                    sanLuongThuHoachBanDau = ruongNongSan.getSanLuongThuHoach();
                });
        for (int i=1; i<=4; i++)
            db.collection("ThongTinTaiKhoan").document(USERID)
                    .collection("ODat").document(String.valueOf(10*ruongNSID+i))
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
                    if (oDat.getSoNgayThuHoach() <= soNgayThuHoachBanDau) img.setImageResource(R.drawable.ns_lua01);
                    if (oDat.getSoNgayThuHoach() <= (soNgayThuHoachBanDau/2)) img.setImageResource(R.drawable.ns_lua02);
                    if (oDat.getSoNgayThuHoach() < 1) img.setImageResource(R.drawable.ns_lua03);
                    break;
                case 2:
                    if (oDat.getSoNgayThuHoach() <= soNgayThuHoachBanDau) img.setImageResource(R.drawable.ns_cachua01);
                    if (oDat.getSoNgayThuHoach() <= soNgayThuHoachBanDau/2) img.setImageResource(R.drawable.ns_cachua02);
                    if (oDat.getSoNgayThuHoach() < 1) img.setImageResource(R.drawable.ns_cachua03);
                    break;
                case 3:
                    if (oDat.getSoNgayThuHoach() <= soNgayThuHoachBanDau) img.setImageResource(R.drawable.ns_carot01);
                    if (oDat.getSoNgayThuHoach() <= soNgayThuHoachBanDau/2) img.setImageResource(R.drawable.ns_carot02);
                    if (oDat.getSoNgayThuHoach() < 1) img.setImageResource(R.drawable.ns_carot03);
                    break;
            }
        }
    }

    @SuppressLint("DefaultLocale")
    public void chonODat(int oDatId){
        oDatID = oDatId;
        db = FirebaseFirestore.getInstance();
        db.collection("ThongTinTaiKhoan").document(USERID)
                .collection("ODat").document(String.valueOf(oDatId))
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    ODat oDat = documentSnapshot.toObject(ODat.class);
                    assert oDat != null;
                    if (!oDat.isMoKhoa()) {
                        react=0;
                        bonPhan=tuoiNuoc=false;
                        imgPhanbon.setEnabled(false);
                        imgNuoc.setEnabled(false);
                        imgReact.setEnabled(true);
                        imgReact.setImageResource(R.drawable.farmcoin);
                    }
                    else if (!oDat.isLamDat()) {
                        react = 1;
                        bonPhan=tuoiNuoc=false;
                        imgPhanbon.setEnabled(false);
                        imgNuoc.setEnabled(false);
                        imgReact.setEnabled(true);
                        imgReact.setImageResource(R.drawable.react_lamdat);
                    }
                    else if (!oDat.isGieoHat()) {
                        react = 2;
                        bonPhan=tuoiNuoc=false;
                        imgPhanbon.setEnabled(false);
                        imgNuoc.setEnabled(false);
                        imgReact.setEnabled(true);
                        imgReact.setImageResource(R.drawable.react_gieohat);
                    }
                    else {
                        imgPhanbon.setEnabled(true);
                        imgNuoc.setEnabled(true);
                        bonPhan = oDat.isBonPhan();
                        tuoiNuoc = oDat.isTuoiNuoc();

                        if (bonPhan) imgPhanbon.setImageResource(R.drawable.react_bonphan_ok);
                        else imgPhanbon.setImageResource(R.drawable.react_bonphan);

                        if (tuoiNuoc) imgNuoc.setImageResource(R.drawable.react_tuoinc_ok);
                        else imgNuoc.setImageResource(R.drawable.react_tuoinc);

                        soNgayThuHoach = oDat.getSoNgayThuHoach();
                        sanLuongThuHoach = oDat.getSanLuongThuHoach();
                        //Toast.makeText(getApplicationContext(), String.format("Còn %d ngày", soNgayThuHoach), Toast.LENGTH_SHORT).show();
                        txt01.setText(String.format("Còn %d ngày", soNgayThuHoach));

                        if (soNgayThuHoach>0){
                            react=3;
                            imgReact.setEnabled(false);
                        } else {
                            react=4;
                            imgReact.setEnabled(true);
                        }
                        imgReact.setImageResource(R.drawable.react_thuhoach);
                    }
                });
    }

    @SuppressLint("DefaultLocale")
    public void  moKhoaODat(int oDatID){
        process=0;
        //trừ tiền
        Thread truTien = new Thread(){
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                db = FirebaseFirestore.getInstance();
                db.collection("ThongTinTaiKhoan").document(USERID)
                        .collection("ThongTinNongTrai").document("ThongTinNongTrai")
                        .update("tongTienNongTrai",FARMCOIN - (100 * O_DAT_UNLOCKED))
                        .addOnSuccessListener(aVoid -> {
                            process+=50;
                            Toast.makeText(getApplicationContext(), String.format("Đã trừ %d tiền nông trại để mở khóa ô đất này.", O_DAT_UNLOCKED * 100), Toast.LENGTH_SHORT).show();
                            if (process==100) loadData();
                            Log.d("TAG", "onSuccess: Update success");
                        })
                        .addOnFailureListener(e -> {
                            process = -100;
                            Toast.makeText(getApplicationContext(),"Lỗi", Toast.LENGTH_SHORT).show();
                        });
            }
        };
        truTien.start();

        //cập nhật trạng thái ô đất
        Thread moKhoa = new Thread(){
            @Override
            public void run() {
                db = FirebaseFirestore.getInstance();
                db.collection("ThongTinTaiKhoan").document(USERID)
                        .collection("ODat").document(String.valueOf(oDatID))
                        .update("moKhoa",true)
                        .addOnSuccessListener(aVoid -> {
                            process+=50;
                            if (process==100) loadData();
                            Log.d("TAG", "onSuccess: Update success");
                        })
                        .addOnFailureListener(e -> {
                            process = -100;
                            Toast.makeText(getApplicationContext(),"Lỗi.", Toast.LENGTH_SHORT).show();
                        });
            }
        };
        moKhoa.start();
    }

    public void lamDat(int oDatID){
        process=0;
        if (STAMINA>0){
            //cập nhật trạng thái ô đất
            Thread lamDat = new Thread(){
                @Override
                public void run() {
                    db = FirebaseFirestore.getInstance();
                    db.collection("ThongTinTaiKhoan").document(USERID)
                            .collection("ODat").document(String.valueOf(oDatID))
                            .update("lamDat",true)
                            .addOnSuccessListener(aVoid -> {
                                process+=50;
                                if (process==100) loadData();
                                if (STAMINA<=0) {
                                    Toast.makeText(getApplicationContext(),"Kiệt sức. Bạn cần nghỉ ngơi.", Toast.LENGTH_SHORT).show();
                                    timeSkip();
                                }
                                Log.d("TAG", "onSuccess: Update success");
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được trạng thái ô đất.", Toast.LENGTH_SHORT).show();
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
                    db.collection("ThongTinTaiKhoan").document(USERID)
                            .collection("ThongTinNongTrai").document("ThongTinNongTrai")
                            .update("giaTriTheLuc",STAMINA-=10)
                            .addOnSuccessListener(aVoid -> {
                                process+=50;
                                if (process==100) loadData();
                                Log.d("TAG", "onSuccess: Update success");
                            })
                            .addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được thông tin nông trại", Toast.LENGTH_SHORT).show());
                }
            };
            truTheLuc.start();
        }
    }

    public void gieoHat(int oDatID){
        if (STAMINA>0){
            switch (ruongNS){
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
            if (FARMCOIN>=tienHatGiong && tienHatGiong!=0){
                process=0;
                //trừ thể lực, tiền nông trại
                Thread truTheLuc = new Thread(){
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void run() {
                        db = FirebaseFirestore.getInstance();
                        db.collection("ThongTinTaiKhoan").document(USERID)
                                .collection("ThongTinNongTrai").document("ThongTinNongTrai")

                                .update("giaTriTheLuc",STAMINA-10,
                                        "tongTienNongTrai",FARMCOIN - tienHatGiong)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(getApplicationContext(), String.format("Farmcoin - %d", tienHatGiong), Toast.LENGTH_SHORT).show();
                                    process+=50;
                                    if (process==100) loadData();
                                    if (STAMINA<=0) {
                                        Toast.makeText(getApplicationContext(),"Kiệt sức. Bạn cần nghỉ ngơi.", Toast.LENGTH_SHORT).show();
                                        timeSkip();
                                    }
                                    Log.d("TAG", "onSuccess: Update success");
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được thông tin nông trại", Toast.LENGTH_SHORT).show();
                                    loadData();
                                    Log.d("TAG", "onFailure: Update fail");
                                });
                    }
                };
                truTheLuc.start();

                //cập nhật trạng thái ô đất
                Thread gieoHat = new Thread(){
                    @Override
                    public void run() {
                        db = FirebaseFirestore.getInstance();
                        db.collection("ThongTinTaiKhoan").document(USERID)
                                .collection("ODat").document(String.valueOf(oDatID))
                                .update("gieoHat",true,
                                        "soNgayThuHoach", soNgayThuHoachBanDau,
                                        "sanLuongThuHoach", sanLuongThuHoachBanDau)
                                .addOnSuccessListener(aVoid -> {
                                    process+=50;
                                    if (process==100) loadData();
                                    Log.d("TAG", "onSuccess: Update success");
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được trạng thái ô đất.", Toast.LENGTH_SHORT).show();
                                    loadData();
                                    Log.d("TAG", "onFailure: Update fail");
                                });
                    }
                };
                gieoHat.start();

            }
        }
    }

    public void thuHoach(int oDatID, String nongSan){
        if (STAMINA>0){
            process=0;
            //trừ thể lực
            Thread truTheLuc = new Thread(){
                @SuppressLint("DefaultLocale")
                @Override
                public void run() {
                    db = FirebaseFirestore.getInstance();
                    db.collection("ThongTinTaiKhoan").document(USERID)
                            .collection("ThongTinNongTrai").document("ThongTinNongTrai")
                            .update("expLevel",FARMEXP + (5 * sanLuongThuHoach),
                                    "giaTriTheLuc", STAMINA - 10,
                                    "tongSoLuongLua", LUA,
                                    "tongSoluongCachua",CACHUA,
                                    "tongSoLuongCaRot",CAROT)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getApplicationContext(),
                                        String.format("Farmexp + %d\n%S + %d",5*sanLuongThuHoach, nongSan, sanLuongThuHoach),
                                        Toast.LENGTH_SHORT).show();
                                process+=50;
                                if (process==100) loadData();
                                if (STAMINA<=0) {
                                    Toast.makeText(getApplicationContext(),"Kiệt sức. Bạn cần nghỉ ngơi.", Toast.LENGTH_SHORT).show();
                                    timeSkip();
                                }
                                Log.d("TAG", "onSuccess: Update success");
                            })
                            .addOnFailureListener(e -> {
                                loadData();
                                Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được thông tin nông trại", Toast.LENGTH_SHORT).show();
                                Log.d("TAG", "onFailure: Update fail");
                            });
                }
            };
            //cập nhật tổng số lượng nông sản
            switch (oDatID / 10){
                case 1:
                    LUA += sanLuongThuHoach;
                    truTheLuc.start();
                    break;
                case 2:
                    CACHUA += sanLuongThuHoach;
                    truTheLuc.start();
                    break;
                case 3:
                    CAROT += sanLuongThuHoach;
                    truTheLuc.start();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + oDatID / 10);
            }

            //reset thông tin ô đất
            Thread thuHoach = new Thread(){
                @Override
                public void run() {
                    db = FirebaseFirestore.getInstance();
                    db.collection("ThongTinTaiKhoan").document(USERID)
                            .collection("ODat").document(String.valueOf(oDatID))
                            .update("lamDat", false,
                                    "gieoHat",false,
                                    "tuoiNuoc", false,
                                    "bonPhan", false,
                                    "soNgayThuHoach",0,
                                    "sanLuongThuHoach",0)
                            .addOnSuccessListener(aVoid -> {
                                tuoiNuoc=bonPhan=false;
                                process+=50;
                                if (process==100) loadData();
                                Log.d("TAG", "onSuccess: Update success");
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được trạng thái ô đất.", Toast.LENGTH_SHORT).show();
                                loadData();
                                Log.d("TAG", "onFailure: Update fail");
                            });
                }
            };
            thuHoach.start();
        }
    }

    public void tuoiNuoc(int oDatID){
        if (STAMINA>=10){
            if (tuoiNuoc){
                Toast.makeText(getApplicationContext(), "Tưới rồi mà, tưới nữa chết cây đó.", Toast.LENGTH_SHORT).show();
            } else {
                process=0;
                //trừ thể lực
                Thread truTheLuc = new Thread(){
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void run() {
                        db = FirebaseFirestore.getInstance();
                        db.collection("ThongTinTaiKhoan").document(USERID)
                                .collection("ThongTinNongTrai").document("ThongTinNongTrai")
                                .update("giaTriTheLuc",STAMINA-10)
                                .addOnSuccessListener(aVoid -> {
                                    process+=50;
                                    if (process==100) loadData();
                                    if (STAMINA<=0) {
                                        Toast.makeText(getApplicationContext(),"Kiệt sức. Bạn cần nghỉ ngơi.", Toast.LENGTH_SHORT).show();
                                        timeSkip();
                                    }
                                    Log.d("TAG", "onSuccess: Update success");
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được thông tin nông trại", Toast.LENGTH_SHORT).show();
                                    Log.d("TAG", "onFailure: Update fail");
                                });
                    }
                };
                truTheLuc.start();

                //update thông tin ô đất
                Thread tuoiNuoc = new Thread(){
                    @Override
                    public void run() {
                        db = FirebaseFirestore.getInstance();
                        db.collection("ThongTinTaiKhoan").document(USERID)
                                .collection("ODat").document(String.valueOf(oDatID))
                                .update("tuoiNuoc",true,
                                        "sanLuongThuHoach",sanLuongThuHoach + sanLuongThuHoachBanDau/soNgayThuHoachBanDau)
                                .addOnSuccessListener(aVoid -> {
                                    process+=50;
                                    if (process==100) loadData();
                                    Log.d("TAG", "onSuccess: Update success");
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được trạng thái ô đất.", Toast.LENGTH_SHORT).show();
                                    Log.d("TAG", "onFailure: Update fail");
                                });
                    }
                };
                tuoiNuoc.start();

            }
        }
    }

    public void bonPhan(int oDatID){
        if (STAMINA>=10){
            if (bonPhan){
                Toast.makeText(getApplicationContext(), "Bón rồi mà, bón nữa chết cây đó.", Toast.LENGTH_SHORT).show();
            } else {
                process=0;
                //trừ thể lực
                Thread truTheLuc = new Thread(){
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void run() {
                        db = FirebaseFirestore.getInstance();
                        db.collection("ThongTinTaiKhoan").document(USERID)
                                .collection("ThongTinNongTrai").document("ThongTinNongTrai")
                                .update("giaTriTheLuc",STAMINA-10)
                                .addOnSuccessListener(aVoid -> {
                                    process+=50;
                                    if (process==100) loadData();
                                    if (STAMINA<=0) {
                                        Toast.makeText(getApplicationContext(),"Kiệt sức. Bạn cần nghỉ ngơi.", Toast.LENGTH_SHORT).show();
                                        timeSkip();//chạy chức năng sang ngày mới
                                    }
                                    Log.d("TAG", "onSuccess: Update success");
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được thông tin nông trại", Toast.LENGTH_SHORT).show();
                                    Log.d("TAG", "onFailure: Update fail");
                                });
                    }
                };
                truTheLuc.start();

                //update thông tin ô đất
                Thread bonPhan = new Thread(){
                    @Override
                    public void run() {
                        db = FirebaseFirestore.getInstance();
                        db.collection("ThongTinTaiKhoan").document(USERID)
                                .collection("ODat").document(String.valueOf(oDatID))
                                .update("bonPhan",true,
                                        "soNgayThuHoach",soNgayThuHoach-1)
                                .addOnSuccessListener(aVoid -> {
                                    process+=50;
                                    if (process==100) loadData();
                                    Log.d("TAG", "onSuccess: Update success");
                                })
                                .addOnFailureListener(e -> {
                                    loadData();
                                    Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được trạng thái ô đất.", Toast.LENGTH_SHORT).show();
                                    Log.d("TAG", "onFailure: Update fail");
                                });
                    }
                };
                bonPhan.start();
            }
        }
    }

    public void sangNgayMoi(){
        process=0;
        //reset giá trị thể lực
        Thread resetTheLuc = new Thread(){
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                db = FirebaseFirestore.getInstance();
                db.collection("ThongTinTaiKhoan").document(USERID)
                        .collection("ThongTinNongTrai").document("ThongTinNongTrai")
                        .update("giaTriTheLuc", 150)
                        .addOnSuccessListener(aVoid -> {
                            process+=40;
                            if (process==100)  {
                                    loadData();
                                    Toast.makeText(getApplicationContext(), "Sáng hôm sau...", Toast.LENGTH_SHORT).show();
                                }
                            Log.d("TAG", "onSuccess: Update success");
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được thông tin nông trại", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "onFailure: Update fail");
                        });
            }
        };
        resetTheLuc.start();

        //tải ds các ô đất đã gieo hạt
        Thread sangNgayMoi = new Thread(){
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                db = FirebaseFirestore.getInstance();
                db.collection("ThongTinTaiKhoan").document(USERID)
                        .collection("ODat").whereEqualTo("gieoHat",true)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                //noinspection ConstantConditions
                                for (QueryDocumentSnapshot snapshot : task.getResult()){
                                    ODat oDat = snapshot.toObject(ODat.class);
                                    if (oDat.isGieoHat() && oDat.getSoNgayThuHoach()>0) {
                                        updateODat(oDat.getoDatID(),oDat.getSoNgayThuHoach());
                                    }
                                }
                                process +=60;
                                if (process==100) {
                                    loadData();
                                    Toast.makeText(getApplicationContext(), "Sáng hôm sau...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getApplicationContext(), "Lỗi!", Toast.LENGTH_SHORT).show();
                            Log.d("Login", "onComplete: Load data error " + e.getMessage());
                        });

            }
        };
        sangNgayMoi.start();
    }

    public void timeSkip(){
        img_bg.setBackgroundResource(R.color.black);
        final Handler handler = new Handler();
        handler.postDelayed(this::sangNgayMoi,3000);
    }

    public void updateODat(int oDatID, int soNgayThuHoach){
        db = FirebaseFirestore.getInstance();
        db.collection("ThongTinTaiKhoan").document(USERID)
                .collection("ODat").document(String.valueOf(oDatID))
                .update("tuoiNuoc", false,
                        "bonPhan", false,
                        "soNgayThuHoach", soNgayThuHoach - 1)
                .addOnSuccessListener(aVoid -> Log.d("TAG", "onSuccess: Update success"))
                .addOnFailureListener(e -> {
                    process = -100;
                    Toast.makeText(getApplicationContext(), "Lỗi không cập nhật được thông tin nông trại", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "onFailure: Update fail");
                });
    }

    @SuppressLint("DefaultLocale")
    public void getODatUnlocked(int oDatID){
        db = FirebaseFirestore.getInstance();
        db.collection("ThongTinTaiKhoan").document(USERID)
                .collection("ODat").whereEqualTo("moKhoa",true)
                .get()
                .addOnCompleteListener(task -> {
                    O_DAT_UNLOCKED=0;
                    if (task.isSuccessful()) {
                        //noinspection ConstantConditions
                        for (QueryDocumentSnapshot ignored : task.getResult()) O_DAT_UNLOCKED++;
                        if (FARMCOIN >= 100*O_DAT_UNLOCKED) moKhoaODat(oDatID);
                        else
                            Toast.makeText(getApplicationContext(), String.format("Ô đất này cần %d tiền nông trại để mở khóa.", 100 * O_DAT_UNLOCKED), Toast.LENGTH_SHORT).show();
                    } else Log.d("TAG", "onComplete: Load data error " + task.getException());
                })
                .addOnFailureListener(e -> Log.d("TAG", "onFailure: Load data error " + e.getMessage()));
    }

    public void giaoHang(int position) {
        DonHang donHang = donHangList.get(position);
        switch (nongSanID) {
            case 1:
                if (LUA >= soLuongMua){
                    db = FirebaseFirestore.getInstance();
                    db.collection("ThongTinTaiKhoan").document(USERID)
                            .collection("ThongTinNongTrai").document("ThongTinNongTrai")
                            .update("expLevel",FARMEXP + 5 * soLuongMua ,
                                    "tongTienNongTrai",FARMCOIN+tienHang,
                                    "tongSoLuongLua",LUA-soLuongMua)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getApplicationContext(),"Đã bán " + soLuongMua + " lúa", Toast.LENGTH_LONG).show();
                                donHang.Create();
                                donHangList.set(position,donHang);
                                donHangAdapter = new DonHangAdapter(getApplicationContext(),donHangList);
                                listView.setAdapter(donHangAdapter);
                                Log.d("TAG", "onSuccess: Update success");
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được thông tin nông trại", Toast.LENGTH_LONG).show();
                                Log.d("TAG", "onFailure: Update fail");
                            });
                } else Toast.makeText(getApplicationContext(), "Không đủ lúa!", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                if (CACHUA >= donHang.getSoLuongMua()){
                    db = FirebaseFirestore.getInstance();
                    db.collection("ThongTinTaiKhoan").document(USERID)
                            .collection("ThongTinNongTrai").document("ThongTinNongTrai")
                            .update("tongTienNongTrai",FARMCOIN+tienHang,
                                    "tongSoLuongLua",CACHUA-soLuongMua)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getApplicationContext(),"Đã bán " + soLuongMua + " cà chua", Toast.LENGTH_LONG).show();
                                donHang.Create();
                                donHangList.set(position,donHang);
                                donHangAdapter = new DonHangAdapter(getApplicationContext(),donHangList);
                                listView.setAdapter(donHangAdapter);
                                Log.d("TAG", "onSuccess: Update success");
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được thông tin nông trại", Toast.LENGTH_LONG).show();
                                Log.d("TAG", "onFailure: Update fail");
                            });

                } else Toast.makeText(getApplicationContext(), "Không đủ cà chua!", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                if (CAROT >= donHang.getSoLuongMua()){
                    db = FirebaseFirestore.getInstance();
                    db.collection("ThongTinTaiKhoan").document(USERID)
                            .collection("ThongTinNongTrai").document("ThongTinNongTrai")
                            .update("tongTienNongTrai",FARMCOIN+tienHang,
                                    "tongSoLuongLua",CAROT-soLuongMua)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getApplicationContext(),"Đã bán " + soLuongMua + " cà rốt", Toast.LENGTH_LONG).show();
                                donHang.Create();
                                donHangList.set(position,donHang);
                                donHangAdapter = new DonHangAdapter(getApplicationContext(),donHangList);
                                listView.setAdapter(donHangAdapter);
                                Log.d("TAG", "onSuccess: Update success");
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getApplicationContext(),"Lỗi không cập nhật được thông tin nông trại", Toast.LENGTH_LONG).show();
                                Log.d("TAG", "onFailure: Update fail");
                            });
                } else Toast.makeText(getApplicationContext(), "Không đủ cà rốt!", Toast.LENGTH_SHORT).show();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + donHang.getNongSanID());
        }
    }

    public void boQua(int position){
        DonHang donHang = donHangList.get(position);
        donHang.Create();
        donHangList.set(position,donHang);
        donHangAdapter = new DonHangAdapter(getApplicationContext(),donHangList);
        listView.setAdapter(donHangAdapter);
    }

    public void updateRuongNS(int idNS, int levelHatGiong, int sanLuong){
        //cập nhật thông tin ruộng nông sản
        Thread updateRNS = new Thread(){
            @Override
            public void run() {
                db = FirebaseFirestore.getInstance();
                db.collection("ThongTinTaiKhoan").document(USERID)
                        .collection("RuongNongSan").document(String.valueOf(idNS))
                        .update("levelHatGiong", levelHatGiong,
                                "sanLuongThuHoach", sanLuong)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(getApplicationContext(), "Đã nâng cấp hạt giống.", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "onSuccess: Update success");
                        })
                        .addOnFailureListener(e -> Log.d("TAG", "onFailure: Update success"));
            }
        };
        updateRNS.start();
    }

    @Override
    public void onBackPressed(){
        if (doubleBackToExitApp){
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitApp = true;
        Toast.makeText(getApplicationContext(), "Nhấn BACK lần nữa để đăng xuất.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitApp = false,2000);
    }
}