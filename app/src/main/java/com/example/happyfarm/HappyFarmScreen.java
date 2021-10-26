package com.example.happyfarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.happyfarm.Adapter.ODatAdapter;
import com.example.happyfarm.Model.ODat;
import com.example.happyfarm.Model.RuongNongSan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;

import static com.example.happyfarm.LoginScreen.USERID;

public class HappyFarmScreen extends AppCompatActivity {

    RuongNongSan ruongNongSan;
    ImageView img_bg, imgLogout, imgCoin, imgIcLua, imgIcCachua, imgIcCarot, imgTimeSkip, imgLua, imgCachua, imgCarot, imgDat1, imgDat2, imgDat3, imgDat4, imgPhanbon, imgReact, imgNuoc, imgDonhang;

    GridView gridView;
    List<ODat> oDatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_happy_farm_screen);

        img_bg = findViewById(R.id.img_bg);
        img_bg.setImageResource(R.drawable.bg_03);

        imgLogout = findViewById(R.id.imgLogout);
        imgLogout.setImageResource(R.drawable.ic_logout);
        imgLogout.setOnClickListener(view -> {

        });

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
        imgLua.setImageResource(R.drawable.ruonglua);

        imgCachua = findViewById(R.id.imgcachua);
        imgCachua.setImageResource(R.drawable.ruongcachua);

        imgCarot = findViewById(R.id.imgcarot);
        imgCarot.setImageResource(R.drawable.ruongcarot);

        gridView = findViewById(R.id.gvODat);
        oDatList.add(1,new ODat(USERID,11,true,true,false,false,false,0, 0));
        oDatList.add(2,new ODat(USERID,12,false,false,false,false,false,0, 0));
        oDatList.add(3,new ODat(USERID,13,false,false,false,false,false,0, 0));
        oDatList.add(4,new ODat(USERID,14,true,false,false,false,false,0, 0));
        ODatAdapter oDatAdapter = new ODatAdapter(getApplicationContext(),oDatList);
        gridView.setAdapter(oDatAdapter);

        imgReact = findViewById(R.id.imgReact);
        imgReact.setImageResource(R.drawable.react_lamdat);
        imgReact.setOnClickListener(view -> {
//            if (flagDat == 0) {
//                flagDat = 1;
//                flagCay = 0;
//                imgReact.setImageResource(R.drawable.gieohat);
//                imgDat.setImageResource(R.drawable.dat01);
//            } else if (flagDat == 1){
//                flagDat=2;
//                flagCay=1;
//                imgReact.setImageResource(R.drawable.thuhoach);
//
//            } else if (flagDat==2){
//                flagDat=3;
//                flagCay=2;
//            } else if (flagDat==3){
//                flagDat=4;
//                flagCay=3;
//            } else if (flagDat==4){
//                flagDat=0;
//                flagCay=0;
//                imgDat.setImageResource(0);
//                flagTuoi=flagBon=0;
//                imgReact.setImageResource(R.drawable.lamdat);
//            }
        });

        imgNuoc = findViewById(R.id.imgTuoinc);
        imgNuoc.setImageResource(R.drawable.react_tuoinc);

        imgNuoc.setOnClickListener(view -> {
//            if (flagDat==0){
//                Toast.makeText(getApplicationContext(),"Bạn chưa làm đất!", Toast.LENGTH_LONG).show();
//            } else {
//                flagTuoi=1;
//                if (flagBon==0){
//                    imgDat.setImageResource(R.drawable.dat02);
//                } else if (flagBon==1){
//                    imgDat.setImageResource(R.drawable.dat04);
//                }
//            }
        });

        imgPhanbon = findViewById(R.id.imgBonphan);
        imgPhanbon.setImageResource(R.drawable.react_bonphan);
        imgPhanbon.setOnClickListener(view -> {
//            if (flagDat==0){
//                Toast.makeText(getApplicationContext(),"Bạn chưa làm đất!", Toast.LENGTH_LONG).show();
//            } else {
//                flagBon=1;
//                if (flagTuoi==0){
//                    imgDat.setImageResource(R.drawable.dat03);
//                } else if (flagTuoi==1){
//                    imgDat.setImageResource(R.drawable.dat04);
//                }
//            }
        });

        imgDonhang = findViewById(R.id.imgOrder);
        imgDonhang.setImageResource(R.drawable.icon_order);
        imgDonhang.setOnClickListener(view -> {



        });

    }

    public void NangCapHatGiong(int ns_id){
        int level = 0;
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        //lấy dữ liệu ruộng
        String id = String.valueOf(ns_id);
        db.collection("RuongNongSan").document(USERID)
                .collection("NongSanID").document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                ruongNongSan = document.toObject(RuongNongSan.class);

                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: Load data fail" + e);
                    }
                });
        level = ruongNongSan.getLevelHatGiong();
        level++;


    }
}