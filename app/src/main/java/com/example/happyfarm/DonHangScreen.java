package com.example.happyfarm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.example.happyfarm.Adapter.DonHangAdapter;
import com.example.happyfarm.Model.DonHang;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.happyfarm.LoginScreen.CACHUA;
import static com.example.happyfarm.LoginScreen.CAROT;
import static com.example.happyfarm.LoginScreen.FARMCOIN;
import static com.example.happyfarm.LoginScreen.LUA;
import static com.example.happyfarm.LoginScreen.USERID;


public class DonHangScreen extends AppCompatActivity implements GiaoHang,BoQua {
    List<DonHang> donHangList;
    DonHangAdapter donHangAdapter;

    GridView gridView;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang_screen);

        db = FirebaseFirestore.getInstance();

        gridView.findViewById(R.id.gvDonHang);

        Thread getListOrder = new Thread(){
            @Override
            public void run() {
                db = FirebaseFirestore.getInstance();
                db.collection("DonHang").document(USERID)
                        .collection("DonHangID")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                donHangList = new ArrayList<>();
                                for (QueryDocumentSnapshot snapshot : Objects.requireNonNull(task.getResult())) {
                                    DonHang donHang = snapshot.toObject(DonHang.class);
                                    donHangList.add(donHang);
                                }
                                donHangAdapter = new DonHangAdapter(getApplicationContext(),donHangList);
                                gridView.setAdapter(donHangAdapter);

                            } else {
                                Toast.makeText(getApplicationContext(), "Lỗi!", Toast.LENGTH_SHORT).show();
                                Log.d("Login", "onComplete: Load data error " + task.getException());
                            }
                        });
            }
        };
        getListOrder.start();

        db.collection("DonHang")
                .addSnapshotListener((value, error) -> {
                    donHangList = new ArrayList<>();
                    //noinspection ConstantConditions
                    for (QueryDocumentSnapshot snapshot : value) {
                        DonHang donHang = snapshot.toObject(DonHang.class);
                        donHangList.add(donHang);
                    }
                    donHangAdapter = new DonHangAdapter(getApplicationContext(), donHangList);
                    gridView.setAdapter(donHangAdapter);
                });

    }



    @Override
    public void giaoHang(int position) {
        DonHang donHang = donHangList.get(position);
        switch (donHang.getNongSanID()) {
            case 1:
                if (LUA >= donHang.getSoLuongMua()){
                    LUA -= donHang.getSoLuongMua();
                    FARMCOIN += donHang.getTienHang();
                    donHang.Create();
                    db.collection("DonHang").document(USERID)
                            .collection("DonHangID").document(String.valueOf(donHang.getDonHangID()))
                            .set(donHang);
                } else Toast.makeText(getApplicationContext(), "Tiền nông trại không đủ!", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                if (CACHUA >= donHang.getSoLuongMua()){
                    CACHUA -= donHang.getSoLuongMua();
                    FARMCOIN += donHang.getTienHang();donHang.Create();
                    db.collection("DonHang").document(USERID)
                            .collection("DonHangID").document(String.valueOf(donHang.getDonHangID()))
                            .set(donHang);

                } else Toast.makeText(getApplicationContext(), "Tiền nông trại không đủ!", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                if (CAROT >= donHang.getSoLuongMua()){
                    CAROT -= donHang.getSoLuongMua();
                    FARMCOIN += donHang.getTienHang();
                    donHang.Create();
                    db.collection("DonHang").document(USERID)
                            .collection("DonHangID").document(String.valueOf(donHang.getDonHangID()))
                            .set(donHang);
                } else Toast.makeText(getApplicationContext(), "Tiền nông trại không đủ!", Toast.LENGTH_SHORT).show();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + donHang.getNongSanID());
        }
    }

    @Override
    public void boQua(int position){
        DonHang donHang = donHangList.get(position);
        donHang.Create();
        db.collection("DonHang").document(USERID)
                .collection("DonHangID").document(String.valueOf(donHang.getDonHangID()))
                .set(donHang);
    }

}