package com.example.happyfarm;

import static com.example.happyfarm.LoginScreen.FARMCOIN;
import static com.example.happyfarm.LoginScreen.USERID;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happyfarm.Adapter.DonHangAdapter;
import com.example.happyfarm.Adapter.SPAdapter;
import com.example.happyfarm.Model.DonHang;
import com.example.happyfarm.Model.SanPham;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShopScreen extends AppCompatActivity {
    TextView txtUID, txtCoin;
    ListView listView;
    ImageView imgCoin;


    List<SanPham> sanPhamList;
    SPAdapter adapter;

    FirebaseFirestore db;


    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_shop_screen);

        txtUID.findViewById(R.id.txtUID);
        txtCoin.findViewById(R.id.txtCoin);
        imgCoin.findViewById(R.id.imgCoin);

        txtUID.setText(USERID);
        txtCoin.setText(String.format("%d", FARMCOIN));
        imgCoin.setImageResource(R.drawable.farmcoin);

        listView.findViewById(R.id.lvSanpham);

        db = FirebaseFirestore.getInstance();

        Thread thread = new Thread(){
            @Override
            public void run() {
                db.collection("Shop").document(USERID)
                        .collection("SanPhamID")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                sanPhamList = new ArrayList<>();
                                for (QueryDocumentSnapshot snapshot : Objects.requireNonNull(task.getResult())) {
                                    SanPham sanPham = snapshot.toObject(SanPham.class);
                                    sanPhamList.add(sanPham);
                                }
                                adapter = new SPAdapter(getApplicationContext(),sanPhamList);
                                listView.setAdapter(adapter);

                            } else {
                                Toast.makeText(getApplicationContext(), "Lỗi!", Toast.LENGTH_SHORT).show();
                                Log.d("Login", "onComplete: Load data error " + task.getException());
                            }
                        });
            }
        };
        thread.start();


    }
}