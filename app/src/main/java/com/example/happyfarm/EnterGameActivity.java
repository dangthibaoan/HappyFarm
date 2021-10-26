package com.example.happyfarm;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import static com.example.happyfarm.LoginScreen.CACHUA;
import static com.example.happyfarm.LoginScreen.CAROT;
import static com.example.happyfarm.LoginScreen.FARMCOIN;
import static com.example.happyfarm.LoginScreen.FARMLEVEL;
import static com.example.happyfarm.LoginScreen.LUA;
import static com.example.happyfarm.LoginScreen.STAMINA;
import static com.example.happyfarm.LoginScreen.USERID;
import static java.lang.Math.log;

public class EnterGameActivity extends AppCompatActivity {

    public static int O_DAT_UNLOCKED;

    FirebaseFirestore db;
    TextView tv1, tv2;
    boolean flag;

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
        flag = intent.getBooleanExtra("flag",false);
        if (!flag) {
            //đưa dữ liệu nông trại lên firestore
            ThongTinTaiKhoan accInfor = new ThongTinTaiKhoan();
            accInfor.setuID(USERID);
            accInfor.Create();
            db.collection("ThongTinNongTrai").document(USERID)
                    .set(accInfor)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "onSuccess: Add food success");
                        }
                    });
            setUp(accInfor.getTongTienNongTrai(),accInfor.getExpLevel(),accInfor.getGiaTriTheLuc(),accInfor.getTongSoLuongLua(),accInfor.getTongSoluongCachua(),accInfor.getTongSoLuongCaRot());

            //đưa dữ liệu ruộng nông sản lên firestore
            RuongNongSan ns_lua = new RuongNongSan(USERID,1,1,5,10);
            String ns_lua_id = String.valueOf(ns_lua.getNongSanID());
            db.collection("RuongNongSan").document(USERID)
                    .collection("NongSanID").document(ns_lua_id)
                    .set(ns_lua);

            RuongNongSan ns_cachua = new RuongNongSan(USERID,2,1,7,11);
            String ns_cachua_id = String.valueOf(ns_lua.getNongSanID());
            db.collection("RuongNongSan").document(USERID)
                    .collection("NongSanID").document(ns_cachua_id)
                    .set(ns_cachua);

            RuongNongSan ns_carot = new RuongNongSan(USERID,3,1,9,12);
            String ns_carot_id = String.valueOf(ns_lua.getNongSanID());
            db.collection("RuongNongSan").document(USERID)
                    .collection("NongSanID").document(ns_carot_id)
                    .set(ns_carot);

            //đưa dữ liệu ô đất lên firestore
            for (int i=1; i<5;i++) {
                ODat lua = new ODat(USERID, 10 + i, false, false, false, false, false, 0, 0);
                lua.Create();
                db.collection("ODat").document(USERID)
                        .collection("ODatID").document(String.valueOf(lua.getoDatID()))
                        .set(lua);
                if (10 + i == 11)
                    db.collection("ODat").document(USERID)
                            .collection("ODatID").document(String.valueOf(lua.getoDatID()))
                            .update("moKhoa", true)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if (lua.getoDatID() > 11)
                                        if (FARMCOIN >= 100 * (O_DAT_UNLOCKED + 1))
                                            FARMCOIN -= 100 * (O_DAT_UNLOCKED + 1);
                                        else
                                            Toast.makeText(getApplicationContext(), "Tiền nông trại không đủ", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onFailure: " + e.getMessage());
                                }
                            });

                ODat cachua = new ODat(USERID, 20 + i, false, false, false, false, false, 0, 0);
                cachua.Create();
                db.collection("ODat").document(USERID)
                        .collection("ODatID").document(String.valueOf(cachua.getoDatID()))
                        .set(lua);

                ODat carot = new ODat(USERID, 30 + i, false, false, false, false, false, 0, 0);
                carot.Create();
                db.collection("ODat").document(USERID)
                        .collection("ODatID").document(String.valueOf(carot.getoDatID()))
                        .set(lua);
                soODatUnlocked();
            }

            //đưa dữ liệu đơn hàng lên firestore
            for(int ii=0;ii<12;ii++){
                DonHang donHang = new DonHang();
                donHang.setUid(USERID);
                donHang.setDonHangID(ii);
                donHang.Create();
                db.collection("DonHang").document(USERID)
                        .collection("DonHangID").document(String.valueOf(donHang.getDonHangID()))
                        .set(donHang);
            }

            //đưa dữ liệu shop lên firestore
            for (int sp=1; sp<6; sp++) {
                SanPham sp_lua = new SanPham(USERID, 10 + sp, "Hạt lúa giống cấp " + sp, "", "Nâng sản lượng thu hoạch lúa từ " + 10 * (sp - 1) + " lên " + 10 * sp, 20 * sp, false);
                String sp_id = String.valueOf(sp_lua.getSanPhamID());
                db.collection("Shop").document(USERID)
                        .collection("SanPhamID").document(sp_id)
                        .set(sp_lua);

                SanPham sp_cachua = new SanPham(USERID, 20 + sp, "Hạt cà chua giống cấp " + sp, "", "Nâng sản lượng thu hoạch cà chua từ " + 11 * (sp - 1) + " lên " + 11 * sp, 25 * sp, false);
                sp_id = String.valueOf(sp_cachua.getSanPhamID());
                db.collection("Shop").document(USERID)
                        .collection("SanPhamID").document(sp_id)
                        .set(sp_cachua);

                SanPham sp_carot = new SanPham(USERID, 30 + sp, "Hạt cà rốt giống cấp " + sp, "", "Nâng sản lượng thu hoạch cà rốt từ " + 12 * (sp - 1) + " lên " + 12 * sp, 20 * sp, false);
                sp_id = String.valueOf(sp_carot.getSanPhamID());
                db.collection("Shop").document(USERID)
                        .collection("SanPhamID").document(sp_id)
                        .set(sp_carot);
            }
            SanPham nuoc_tang_luc = new SanPham(USERID,1,"Nước tăng lực","","Cộng 50 giá trị thể lực",2000,false);
            String sp_id = String.valueOf(nuoc_tang_luc.getSanPhamID());
            db.collection("Shop").document(USERID)
                    .collection("SanPhamID").document(sp_id)
                    .set(nuoc_tang_luc);

            startActivity(new Intent(EnterGameActivity.this,HappyFarmScreen.class));
        }
        else {
            soODatUnlocked();
            db.collection("ThongTinNongTrai").whereEqualTo("uID",USERID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                //noinspection ConstantConditions
                                for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                    O_DAT_UNLOCKED++;
                                }
                            } else {
                                Log.d("TAG", "onComplete: Load data error " + task.getException());
                            }
                        }
                    });
            startActivity(new Intent(EnterGameActivity.this,HappyFarmScreen.class));
        }
    }

    private void setUp(int fCoin, int exp, int sta, int lua, int cachua, int carot){
        FARMCOIN = fCoin;
        FARMLEVEL = (int) ((log((exp / 100) + 1) / log(2)) - 1);
        STAMINA = sta;
        LUA = lua;
        CACHUA = cachua;
        CAROT = carot;
    }

    public void soODatUnlocked(){
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
                         } else {
                             Log.d("TAG", "onComplete: Load data error " + task.getException());
                         }
                     }
                 });
     }
}