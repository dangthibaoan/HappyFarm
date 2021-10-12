package com.example.happyfarm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.happyfarm.Model.DonHang;
import com.example.happyfarm.Model.ODat;
import com.example.happyfarm.Model.RuongNongSan;
import com.example.happyfarm.Model.SanPham;
import com.example.happyfarm.Model.ThongTinTaiKhoan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.happyfarm.LoginScreen.CACHUA;
import static com.example.happyfarm.LoginScreen.CAROT;
import static com.example.happyfarm.LoginScreen.FARMCOIN;
import static com.example.happyfarm.LoginScreen.FARMLEVEL;
import static com.example.happyfarm.LoginScreen.LUA;
import static com.example.happyfarm.LoginScreen.STAMINA;
import static com.example.happyfarm.LoginScreen.USERID;

public class WaitActivity extends AppCompatActivity {

    public static int O_DAT_UNLOCKED;

    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_wait);

        Intent intent = getIntent();
        flag = intent.getBooleanExtra("flag",false);
        if (!flag) {
            KhoiTao();
            startActivity(new Intent(WaitActivity.this,HappyFarmScreen.class));
        }
        else {
            soODatUnlocked();
            startActivity(new Intent(WaitActivity.this,HappyFarmScreen.class));
        }
    }

    public void KhoiTao(){
        //đưa dữ liệu nông trại lên firestore
        ThongTinTaiKhoan accInfor = new ThongTinTaiKhoan(USERID,0,0,100,0,0,0);
        accInfor.Create();
        FARMCOIN=FARMLEVEL=LUA=CACHUA=CAROT=0;
        STAMINA=100;
        O_DAT_UNLOCKED=1;

        //đưa dữ liệu ruộng nông sản lên firestore
        RuongNongSan ns_lua = new RuongNongSan(USERID,1,1,5,10);
        ns_lua.Create();

        RuongNongSan ns_cachua = new RuongNongSan(USERID,2,1,7,11);
        ns_cachua.Create();

        RuongNongSan ns_carot = new RuongNongSan(USERID,3,1,9,12);
        ns_carot.Create();

        //đưa dữ liệu ô đất lên firestore
        for (int i=1; i<5;i++){
            ODat lua = new ODat(USERID,10+i,false,false,false,false,false,0,0);
            lua.Create();
            if (10+i==11) lua.MoKhoa();
            ODat cachua = new ODat(USERID,20+i,false,false,false,false,false,0,0);
            cachua.Create();
            ODat carot = new ODat(USERID,30+i,false,false,false,false,false,0,0);
            carot.Create();
        }

        //đưa dữ liệu đơn hàng lên firestore
        for(int i=0;i<12;i++){
            DonHang donHang = new DonHang();
            donHang.setUid(USERID);
            donHang.setDonHangID(i);
            donHang.Create();
        }

        //đưa dữ liệu shop lên firestore
        for (int i=1; i<6; i++){
            SanPham lua = new SanPham(USERID,10+i,"Hạt lúa giống cấp " + i,"","Nâng sản lượng thu hoạch lúa từ " + 10*(i-1) + " lên "+ 10*i,20*i,false);
            lua.Create();
            SanPham cachua = new SanPham(USERID,20+i,"Hạt cà chua giống cấp " + i,"","Nâng sản lượng thu hoạch cà chua từ " + 11*(i-1) + " lên "+ 11*i,25*i,false);
            cachua.Create();
            SanPham carot = new SanPham(USERID,30+i,"Hạt cà rốt giống cấp " + i,"","Nâng sản lượng thu hoạch cà rốt từ " + 12*(i-1) + " lên "+ 12*i,20*i,false);
            carot.Create();
        }
        SanPham nuoc_tang_luc = new SanPham(USERID,1,"Nước tăng lực","","Cộng 50 giá trị thể lực",2000,false);
    }

    public void soODatUnlocked(){
         FirebaseFirestore db;
         db = FirebaseFirestore.getInstance();

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